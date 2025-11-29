package com.main.model;

public enum Role {
    ADMINISTRATEUR("Administrateur", 4, "Accès complet au système"),
    CHEF_DEPARTEMENT("Chef de département", 3, "Gestion d'un département"),
    CHEF_PROJET("Chef de projet", 2, "Gestion de projets"),
    EMPLOYE("Employé", 1, "Accès limité aux informations personnelles");

    private final String displayName;
    private final int level;
    private final String description;

    Role(String displayName, int level, String description) {
        this.displayName = displayName;
        this.level = level;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasLevelGreaterOrEqual(Role other) {
        return this.level >= other.level;
    }

    public static Role fromLevel(int level) {
        for (Role role : Role.values()) {
            if (role.level == level) {
                return role;
            }
        }
        return EMPLOYE;
    }

    public static Role fromString(String roleStr) {
        if (roleStr == null)
            return null;

        for (Role r : Role.values()) {
            if (r.name().equalsIgnoreCase(roleStr) ||
                    r.displayName.equalsIgnoreCase(roleStr)) {
                return r;
            }
        }
        return null;
    }
}