package me.serce.solidity.lang.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import me.serce.solidity.lang.SolidityFileType
import org.intellij.lang.annotations.Language

class SolPsiFactory(val project: Project) {
  fun createIdentifier(name: String): PsiElement {
    return createFromText<SolContractDefinition>("contract $name {}")?.identifier
      ?: error("Failed to create identifier: `$name`")
  }

  fun createStruct(structBody: String): SolStructDefinition {
    return createFromText<SolStructDefinition>("contract dummystruct$1 { $structBody }")
      ?: error("Failed to create struct: `$structBody`")
  }

  fun createContract(@Language("Solidity") contractBody: String): SolContractDefinition {
    return createFromText<SolContractDefinition>(contractBody) ?: error("Failed to create contract: `$contractBody`")
  }

  private inline fun <reified T : SolElement> createFromText(code: String): T? =
    PsiFileFactory.getInstance(project)
      .createFileFromText("DUMMY.sol", SolidityFileType, code)
      .childOfType<T>()
}
