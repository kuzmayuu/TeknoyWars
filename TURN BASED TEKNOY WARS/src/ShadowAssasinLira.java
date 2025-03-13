import java.util.Scanner;

class ShadowAssassinLira extends Hero {
    private int vanishTurnsRemaining = 0;  // Tracks remaining turns of Vanish effect
    private int[] skillPP = {Integer.MAX_VALUE, 8, 5, 3};
    private final int[] maxPP = {Integer.MAX_VALUE, 8, 5, 3};
    private int turnCounter = 0;

    public ShadowAssassinLira() {
        super("Lira", "Shadow Assassin", 750, 400, "Lira was trained in the secretive Order of the Night, a group of assassins that controls the shadows.");
    }

    @Override
    public void displayStats() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Hero: " + getName() + " | Type: Shadow Assassin");
        System.out.println("HP: " + getHp() + " | Mana: " + getMana());
        System.out.println("Description: " + getDescription());
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void displaySkills() {
        System.out.println("Skill Set:");
        System.out.println("1. Shadow Strike (Mana Cost 0, PP: ∞) - A stealthy strike dealing 50 damage.");
        System.out.println("2. Shadow Step (Mana Cost 50, PP: " + skillPP[1] + ") - Teleports behind the target, dealing 100 damage.");
        System.out.println("3. Nightfall Strike (Mana Cost 70, PP: " + skillPP[2] + ") - Unleashes a flurry of shadow-infused daggers, dealing 150 damage.");
        System.out.println("4. Vanish (Mana Cost 50, PP: " + skillPP[3] + ") - Vanishes into the shadows, becoming untargetable for the next attack.");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void useSkill(int skillNumber, Hero enemyHero) {
        Scanner scanner = new Scanner(System.in); 
        int skillIndex = skillNumber - 1;

        while (true) {
            try {
                if (skillIndex >= 0 && skillIndex < skillPP.length && skillPP[skillIndex] > 0) {
                    break; 
                }
                System.out.print(getName() + " cannot use this skill (PP depleted) or invalid skill! Try again: ");
                skillNumber = scanner.nextInt(); 
                skillIndex = skillNumber - 1; 
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid skill number.");
                scanner.nextLine(); 
            }
        }

        int manaCost = getSkillManaCost(skillNumber);
        if (hasEnoughMana(manaCost)) {
            useMana(manaCost);
            skillPP[skillIndex]--;
            if (skillNumber == 1) {
                System.out.println(getName() + " used Shadow Strike!");
                enemyHero.takeDamage(getSkillDamage(skillNumber));
                recoverMana(50);
            } else if (skillNumber == 4) {
                vanish();  
            } else {
                System.out.println(getName() + " used skill " + skillNumber + "!");
                enemyHero.takeDamage(getSkillDamage(skillNumber));
            }
        } else {
            System.out.println("Not enough mana to use this skill.");
        }
        turnCounter++;
        if (turnCounter >= 3) {
            recoverPP();
            turnCounter = 0;
        }
        manageVanish();  
    }

    @Override
    public int getSkillDamage(int skillChoice) {
        switch (skillChoice) {
            case 1: return 50;
            case 2: return 100;
            case 3: return 150;
            case 4: return 0;  
            default: return 0;
        }
    }

    @Override
    public void takeDamage(int damage) {
        setHp(getHp() - damage);
        if (getHp() < 0) setHp(0);
    }

    @Override
    public boolean isAlive() {
        return getHp() > 0;
    }
    public void resetPP() {
        skillPP = new int[]{Integer.MAX_VALUE, 8, 5, 3}; 
    }


    public void manageVanish() {
        if (vanishTurnsRemaining > 0) {
            vanishTurnsRemaining--;
            if (vanishTurnsRemaining == 0) {
                System.out.println(getName() + " reappears from the shadows!");
            }
        }
    }

    public void vanish() {
        vanishTurnsRemaining = 2;  
        System.out.println(getName() + " vanishes into the shadows, becoming untargetable for the next attack!");
    }

    public boolean isVanishActive() {
        return vanishTurnsRemaining > 0;
    }

    public void recoverMana(int amount) {
        setMana(Math.min(getMana() + amount, getMaxMana()));
    }

    @Override
    public int getSkillManaCost(int skillChoice) {
        switch (skillChoice) {
            case 1: return 0;
            case 2: return 50;
            case 3: return 70;
            case 4: return 50;
            default: return 0;
        }
    }
   
    private void recoverPP() {
        for (int i = 1; i < skillPP.length; i++) {
            if (skillPP[i] < maxPP[i]) {
                skillPP[i]++; 
            }
        }
        System.out.println(getName() + "'s PP for skills has been restored by 1 for each skill (limited to max PP)!");
    }
}