package detektive

import detektive.javacompat.AnnotatePublicApisWithJvmOverloads
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class DetektiveRuleSetProvider : RuleSetProvider {

  override val ruleSetId: String = "detective"

  override fun instance(config: Config): RuleSet {
    return RuleSet(
      ruleSetId,
      listOf(
        AnnotatePublicApisWithJvmOverloads(config)
      )
    )
  }
}
