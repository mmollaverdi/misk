package detektive.javacompat

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import io.gitlab.arturbosch.detekt.api.internal.RequiresTypeResolution
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor
import org.jetbrains.kotlin.descriptors.Visibilities
import org.jetbrains.kotlin.descriptors.effectiveVisibility
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.containingClassOrObject
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.descriptorUtil.isPublishedApi
import kotlin.reflect.KClass

@RequiresTypeResolution
class AnnotatePublicApisWithJvmOverloads(config: Config) : Rule(config) {

  override val issue = Issue(
    javaClass.simpleName,
    Severity.Defect,
    "Public functions and constructors with default values should be annotated with @JvmOverloads",
    Debt.FIVE_MINS
  )

  override fun visitNamedFunction(function: KtNamedFunction) {
    if (bindingContext == BindingContext.EMPTY) {
      return
    }
    if (isApplicable(function, bindingContext[BindingContext.DECLARATION_TO_DESCRIPTOR, function])) {
      if (!function.annotationEntries.any { it.isOfType(JvmOverloads::class) }) {
        report(
          CodeSmell(
            issue,
            Entity.atName(function),
            "Public API function '${function.nameAsSafeName}' with default arguments, but without @JvmOverloads annotation"
          )
        )
      }
    }
    super.visitNamedFunction(function)
  }

  private fun isApplicable(
    element: KtNamedFunction,
    descriptor: DeclarationDescriptor?
  ): Boolean {
    val debug: (String) -> Unit = { message -> if(element.name == "header") println(message) }

    debug("element name: ${element.name}")
    val containingType = element.containingClassOrObject
    debug("containingType: $containingType")

    // Function is inside a class and not an interface
    if (containingType !is KtClass || containingType.isInterface()) return false
//    if (element is KtFunction && element.isLocal) return false
//    if (element is KtProperty && element.isLocal) return false

    val callableMemberDescriptor = descriptor as? CallableMemberDescriptor
    debug("callableMemberDescriptor: ${callableMemberDescriptor?.name}")
    val visibility = callableMemberDescriptor?.effectiveVisibility()?.toVisibility()
    debug("visibility: ${visibility?.name}")

    debug("valueParameters: ${element.valueParameters}")
    // Function has any parameters with default values
    if (!element.valueParameters.any { it.hasDefaultValue() }) return false

    // Function is public
    return visibility?.isPublicAPI == true ||
      (visibility == Visibilities.Internal && callableMemberDescriptor.isPublishedApi())
  }
}


private fun KtAnnotationEntry.isOfType(annotation: KClass<out Annotation>) =
  shortName?.identifier == annotation.simpleName

