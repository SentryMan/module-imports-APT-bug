package io.avaje.modules.example;

import java.util.List;

import io.avaje.modules.ReadJavadoc;

/**
 * @param tasks the tasks required to repair the ship and defeat the impostors
 */
@ReadJavadoc
public record CrewMate(
    /** the tasks required to repair the ship and defeat the impostors */
    List<String> tasks) {}
