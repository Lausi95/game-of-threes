package de.lausi95.gameofthrees

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.library.Architectures
import org.junit.jupiter.api.Test

class Architecture {

  @Test
  fun testArchitecture() {
    val importedClasses = ClassFileImporter()
      .withImportOption(ImportOption.DoNotIncludeTests())
      .importPackages("de.lausi95.gameofthrees")

    Architectures.onionArchitecture().withOptionalLayers(true)
      .domainModels("de.lausi95.gameofthrees.domain.model.*")
      .applicationServices("de.lausi95.gameofthrees.application")
      .adapter("http", "de.lausi95.gameofthrees.adapter.http")
      .adapter("kafka", "de.lausi95.gameofthrees.adapter.kafka.*")
      .adapter("internal", "de.lausi95.gameofthrees.adapter.internal")
      .check(importedClasses)
  }
}
