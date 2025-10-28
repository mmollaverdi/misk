package misk.mockito

import com.google.inject.Provider
import misk.testing.TestFixture
import org.mockito.Mockito

class MockitoTestFixture(private val mockProvider: Provider<out Any>) : TestFixture {
  override fun reset() {
    Mockito.reset(mockProvider.get())
  }
}

// inline fun <reified T : Any> ReusableTestModule.bindMock() {
//   val mock = mock<T>()
//   bind<BarbClient>().toInstance(mock)
//   multibind<TestFixture>().toInstance(MockitoTestFixture { mock })
// }