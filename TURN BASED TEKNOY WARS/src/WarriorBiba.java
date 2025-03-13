import java.util.Scanner;

class WarriorBiba extends Hero {
    private int[] skillPP = {Integer.MAX_VALUE, 8, 4, 3}; 
    private final int[] maxPP = {Integer.MAX_VALUE, 8, 4, 3}; 
    private int turnCounter = 0; 

    public WarriorBiba() {
        super("Biba", "Warrior", 800, 400, "Biba, the son of a fallen general, seeks vengeance as a wandering warrior against Eldoria's corrupt leaders.");
    }

    @Override
    public void displayStats() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Hero: " + getName() + " | Type: Warrior");
        System.out.println("HP: " + getHp() + " | Mana: " + getMana());
        System.out.println("Description: " + getDescription());
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public boolean isAlive() {
        return getHp() > 0;
    }

    @Override
    public void displaySkills() {
        System.out.println("Skill Set:");
        System.out.println("1. Shield Bash (Mana Cost: 0, PP: ∞) - A quick strike with the shield that deals 50 damage.");
        System.out.println("2. Whirlwind Slash (Mana Cost: 50, PP: " + skillPP[1] + ") - Spins wildly with a two-handed weapon, hitting all enemies for 80 damage each.");
        System.out.println("3. Cleave (Mana Cost: 80, PP: " + skillPP[2] + ") - A sweeping attack that hits all enemies for 100 damage.");
        System.out.println("4. Berserker Strike (Mana Cost: 100, PP: " + skillPP[3] + ") - Unleashes a reckless, powerful strike, dealing 200 damage to a single target, with 50 recoil damage.");
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

        if (skillPP[skillIndex] <= 0) {
            System.out.println("Not enough PP for this skill!");
            return;
        }

        if (!hasEnoughMana(manaCost)) {
            System.out.println("Not enough Mana to use this skill!");
            return;
        }

        skillPP[skillIndex]--;
        useMana(manaCost);

        switch (skillNumber) {
            case 1 -> {
                int damage = 50;
                enemyHero.takeDamage(damage);
                recoverMana(50); 
                System.out.println(getName() + " used Shield Bash and dealt " + damage + " damage!");
            }
            case 2 -> {
                int damage = 80;
                System.out.println(getName() + " used Whirlwind Slash, hitting all enemies for " + damage + " damage each!");
                enemyHero.takeDamage(damage); 
            }
            case 3 -> {
                int damage = 100;
                enemyHero.takeDamage(damage);
                System.out.println(getName() + " used Cleave and dealt " + damage + " damage to the enemy!");
            }
            
            case 4 -> {
                int damage = 200;
                enemyHero.takeDamage(damage);
                takeDamage(50); 
                System.out.println(getName() + " used Berserker Strike, dealing " + damage + " damage but took 50 recoil damage!");
            }
        }

        turnCounter++;
        if (turnCounter >= 3) {
            recoverPP();
            turnCounter = 0; 
        }
    }

    @Override
    public int getSkillDamage(int skillNumber) {
        return switch (skillNumber) {
            case 1 -> 50;
            case 2 -> 80;
            case 3 -> 100;
            case 4 -> 200;
            default -> 0;
        };
    }

    @Override
    public int getSkillManaCost(int skillNumber) {
        return switch (skillNumber) {
            case 1 -> 0;
            case 2 -> 50;
            case 3 -> 80;
            case 4 -> 100;
            default -> 0;
        };
    }
    public void resetPP() {
        skillPP = new int[]{Integer.MAX_VALUE, 8, 4, 3}; 
    }

    @Override
    public void takeDamage(int damage) {
    setHp(getHp() - damage); 
    if (getHp() < 0) {
        setHp(0); 
    }
    System.out.println(getName() + " takes " + damage + " damage.");
}


public void recoverMana(int amount) {
    setMana(getMana() + amount); 
    if (getMana() > 400) { 
        setMana(400);
    }
}

    private void recoverPP() {
        for (int i = 1; i < skillPP.length; i++) {
            if (skillPP[i] < maxPP[i]) {
                skillPP[i]++;
            }
        }
        System.out.println(getName() + "'s PP for all skills (except Shield Bash) has been restored by 1.");
    }

}


