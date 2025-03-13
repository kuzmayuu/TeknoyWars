import java.util.*;

public class Main {
    public static void main(String[] args) {
        BlademasterKaito kaito = new BlademasterKaito();
        PyromancerSelena selena = new PyromancerSelena();
        ShadowAssassinLira lira = new ShadowAssassinLira();
        PaladinLionhart lionhart = new PaladinLionhart();
        NecromancerEzekiel ezekiel = new NecromancerEzekiel();  
        WarriorBiba biba = new WarriorBiba();

        Hero[] heroes = {kaito, selena, lira, lionhart, ezekiel, biba};
        Scanner scanner = new Scanner(System.in);

        System.out.println("TEKNOY WARS THE CLASH OF COURSES");

        
        while (true) {
            System.out.println("\n\n-----------------------------------");
            System.out.println("-----------------------------------");
            System.out.println("----        MAIN MENU          ----");
            System.out.println("----     1. Player vs AI       ----");
            System.out.println("----     2. Player vs Player   ----");
            System.out.println("----     3. Credits            ----");
            System.out.println("----     4. Exit               ----");
            System.out.println("-----------------------------------");
            System.out.println("-----------------------------------\n");

           
            int menuChoice = -1; 

            do {
                System.out.print("Select an option (1-4): ");
                try {
                    menuChoice = scanner.nextInt();
                    if (menuChoice < 1 || menuChoice > 4) {
                        System.out.println("Please select a valid option (1-4).");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please select a valid option (1-4).");
                    scanner.next(); 
                }
            } while (menuChoice < 1 || menuChoice > 4); 

            switch (menuChoice) {
                case 1: 
                    playAgainstAI(heroes, scanner);
                    break;
                case 2: 
                    playAgainstPlayer(heroes, scanner);
                    break;
                case 3:
                System.out.println("-----------------------------------");
                System.out.println("-----------------------------------");
                System.out.println("----        Credits:           ----");
                System.out.println("----   Arnold Michael Tabada   ----");
                System.out.println("----   John Timothy Cabuguas   ----");
                System.out.println("----      Arnold Canoy         ----");
                System.out.println("-----------------------------------");
                System.out.println("-----------------------------------");
                
                int backToMenu;
            do {
                System.out.print("Press 0 to go back to the main menu: ");
                    try {
                    backToMenu = scanner.nextInt();
                    if (backToMenu != 0) {
                    System.out.println("Invalid input, please press 0.");
                        }
                    } catch (InputMismatchException e) {
                System.out.println("Invalid input, please press 0.");
                scanner.next(); 
                backToMenu = -1; 
                    }
                } while (backToMenu != 0);
                break;
                case 4:
                    System.out.println("Exiting the game. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please select again.");
            }
        }
    }

    private static void playAgainstAI(Hero[] heroes, Scanner scanner) {
        boolean replay = true;
        while (replay) {
            Hero selectedHero = selectHero(heroes, scanner);
            if (selectedHero == null) return;

            Hero enemyHero = selectRandomEnemy(heroes, selectedHero);
            System.out.println("\nAn enemy hero has appeared!\n");
            enemyHero.displayStats();

            battle(selectedHero, enemyHero, scanner, true);

            resetHeroStats(selectedHero);
            resetHeroStats(enemyHero);

            replay = askToReplay(scanner);
        }
    }

    private static void playAgainstPlayer(Hero[] heroes, Scanner scanner) {
        boolean replay = true;
        while (replay) {
            Hero playerOne = selectHero(heroes, scanner);
            if (playerOne == null) return;
    
            System.out.println("Player 1 has selected: " + playerOne.getName()); 
    
            Hero playerTwo = null;
            while (true) {
                playerTwo = selectHero(heroes, scanner);
                if (playerTwo == null) return; 
    
                if (playerTwo != playerOne) {
                    break; 
                }
    
                System.out.println("You cannot select the same hero. Try again.");
            }
    
            System.out.println("Player 2 has selected: " + playerTwo.getName()); 
    
            battle(playerOne, playerTwo, scanner, false);
    
            resetHeroStats(playerOne);
            resetHeroStats(playerTwo);
    
            replay = askToReplay(scanner);
        }
    }
    
    private static Hero selectHero(Hero[] heroes, Scanner scanner) {
        System.out.println("\n-----------------------------------");
        System.out.println("----         =HEROES=          ----");
        System.out.println("----       1. Kaito            ----");
        System.out.println("----       2. Selena           ----");
        System.out.println("----       3. Lira             ----");
        System.out.println("----       4. Lionhart         ----");
        System.out.println("----       5. Ezekiel          ----");
        System.out.println("----       6. Biba             ----");
        System.out.println("-----------------------------------\n");
    
        int heroChoice = -1; 
        while (true) {
            System.out.print("Select a hero: ");
            try {
                heroChoice = scanner.nextInt();
                if (heroChoice < 1 || heroChoice > 6) {
                    System.out.println("Invalid hero selection. Please select a number between 1-6.");
                    continue; 
                }
                break; 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
            }
        }
    
        Hero selectedHero = heroes[heroChoice - 1];
        selectedHero.displayStats();
        selectedHero.displaySkills();
        return selectedHero;
    }
    
    private static Hero selectRandomEnemy(Hero[] heroes, Hero selectedHero) {
        Random random = new Random();
        Hero enemyHero;
        do {
            enemyHero = heroes[random.nextInt(heroes.length)];
        } while (enemyHero == selectedHero);
        return enemyHero;
    }

    private static void battle(Hero player1, Hero player2, Scanner scanner, boolean isAI) {
    int turnCounter = 0;
    while (player1.isAlive() && player2.isAlive()) {
        turnCounter++;
        System.out.println("\n--- Round " + turnCounter + " ---");

        if (player1.isStunned()) {
            System.out.println(player1.getName() + " is stunned and cannot act!");
            player1.clearStun(); 
        } else {
            playerTurn(player1, player2, scanner, isAI);
            if (player1 instanceof PaladinLionhart && ((PaladinLionhart) player1).hasExtraTurn()) {
                System.out.println(player1.getName() + " gets an extra turn!");
                continue; 
            }
        }

        if (!player2.isAlive()) break;

        if (isAI) {
            aiTurn(player2, player1);
        } else {
            if (player2.isStunned()) {
                System.out.println(player2.getName() + " is stunned and cannot act!");
                player2.clearStun(); 
            } else {
                playerTurn(player2, player1, scanner, isAI);
            }
        }
    }
    displayBattleOutcome(player1, player2, isAI);
    turnCounter = 0;
}

private static void playerTurn(Hero attacker, Hero defender, Scanner scanner, boolean isAI) {
    System.out.println("\n" + attacker.getName() + "'s turn!");
    attacker.displaySkills();
    
    int skillChoice = -1;
    int manaCost;

    while (true) {
        try {
            System.out.print("Select a skill (1-4): ");
            skillChoice = scanner.nextInt();
            if (skillChoice < 1 || skillChoice > 4) {
                System.out.println("Invalid skill selection. Please select a number between 1 and 4.");
                continue; 
            }
            manaCost = attacker.getSkillManaCost(skillChoice);
            if (!attacker.hasEnoughMana(manaCost)) {
                System.out.println("Not enough mana! Try again.");
                continue; 
            }
            break; 
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); 
        }
    }

    boolean isVanishActive = defender instanceof ShadowAssassinLira && ((ShadowAssassinLira) defender).isVanishActive();
    if (isVanishActive) {
        System.out.println(defender.getName() + " dodged the attack due to Vanish!");
    } else {
        attacker.useSkill(skillChoice, defender);
    }

    displayStats(attacker, defender);

    if (defender instanceof ShadowAssassinLira) {
        ((ShadowAssassinLira) defender).manageVanish();
    }
    if (attacker instanceof ShadowAssassinLira) {
        ((ShadowAssassinLira) attacker).recoverMana(50);  
    }
}

private static void aiTurn(Hero ai, Hero player) {
    System.out.println("\n" + ai.getName() + "'s turn (AI)!");

    ai.displaySkills();
    Random rand = new Random();
    int skillChoice;

    do {
        skillChoice = rand.nextInt(4) + 1;
    } while (!ai.hasEnoughMana(ai.getSkillManaCost(skillChoice)));

    boolean isVanishActive = player instanceof ShadowAssassinLira && ((ShadowAssassinLira) player).isVanishActive();
    if (isVanishActive) {
        System.out.println(player.getName() + " dodged the attack due to Vanish!");
    } else {
        ai.useSkill(skillChoice, player);
    }

    displayStats(ai, player);

    if (player instanceof ShadowAssassinLira) {
        ((ShadowAssassinLira) player).manageVanish();
    }
    if (ai instanceof ShadowAssassinLira) {
        ((ShadowAssassinLira) ai).recoverMana(50);  
    }
}

    private static void displayStats(Hero attacker, Hero defender) {
        System.out.println("-----------------------------------------------");
        System.out.println(attacker.getName() + " HP: " + attacker.getHp() + " | Mana: " + attacker.getMana());
        System.out.println(defender.getName() + " HP: " + defender.getHp() + " | Mana: " + defender.getMana());
        System.out.println("-----------------------------------------------");
    }

    private static void displayBattleOutcome(Hero player1, Hero player2, boolean isAI) {
        if (player1.isAlive()) {
            if (isAI) {
                System.out.println();
                System.out.println("=█████=█████====███████====█████==█████====█████===███===█████=█████=██████===█████=███");
                System.out.println("░░███=░░███===███░░░░░███=░░███==░░███====░░███===░███==░░███=░░███=░░██████=░░███=░███");
                System.out.println("=░░███=███===███=====░░███=░███===░███=====░███===░███===░███==░███==░███░███=░███=░███");
                System.out.println("==░░█████===░███======░███=░███===░███=====░███===░███===░███==░███==░███░░███░███=░███");
                System.out.println("===░░███====░███======░███=░███===░███=====░░███==█████==███===░███==░███=░░██████=░███");
                System.out.println("====░███====░░███=====███==░███===░███======░░░█████░█████░====░███==░███==░░█████=░░░=");
                System.out.println("====█████====░░░███████░===░░████████=========░░███=░░███======█████=█████==░░█████=███");
                System.out.println("===░░░░░=======░░░░░░░======░░░░░░░░===========░░░===░░░======░░░░░=░░░░░====░░░░░=░░░=");
                System.out.println();

            } else {
                System.out.println();

                System.out.println("=███████████==█████=========█████████===█████=█████=██████████=███████████======████===");
                System.out.println("░░███░░░░░███░░███=========███░░░░░███=░░███=░░███=░░███░░░░░█░░███░░░░░███====░░███===");
                System.out.println("=░███====░███=░███========░███====░███==░░███=███===░███==█=░==░███====░███=====░███===");
                System.out.println("=░██████████==░███========░███████████===░░█████====░██████====░██████████======░███===");
                System.out.println("=░███░░░░░░===░███========░███░░░░░███====░░███=====░███░░█====░███░░░░░███=====░███===");
                System.out.println("=░███=========░███======█=░███====░███=====░███=====░███=░===█=░███====░███=====░███===");
                System.out.println("=█████========███████████=█████===█████====█████====██████████=█████===█████====█████==");
                System.out.println("░░░░░========░░░░░░░░░░░=░░░░░===░░░░░====░░░░░====░░░░░░░░░░=░░░░░===░░░░░====░░░░░===");
                System.out.println("=======================================================================================");
                System.out.println("=======================================================================================");
                System.out.println("=█████===███===█████=█████=██████===█████==█████████=====███===========================");
                System.out.println("░░███===░███==░░███=░░███=░░██████=░░███==███░░░░░███===░███===========================");
                System.out.println("=░███===░███===░███==░███==░███░███=░███=░███====░░░====░███===========================");
                System.out.println("=░███===░███===░███==░███==░███░░███░███=░░█████████====░███===========================");
                System.out.println("=░░███==█████==███===░███==░███=░░██████==░░░░░░░░███===░███===========================");
                System.out.println("==░░░█████░█████░====░███==░███==░░█████==███====░███===░░░============================");
                System.out.println("====░░███=░░███======█████=█████==░░█████░░█████████=====███===========================");
                System.out.println("=====░░░===░░░======░░░░░=░░░░░====░░░░░==░░░░░░░░░=====░░░============================");
                System.out.println();

            }
        } else {
            if (isAI) {
                System.out.println();
                System.out.println("=█████=█████====███████====█████==█████====█████==========███████=====█████████==██████████=███");
                System.out.println("░░███=░░███===███░░░░░███=░░███==░░███====░░███=========███░░░░░███==███░░░░░░███░███░░░░░█░███");
                System.out.println("=░░███=███===███=====░░███=░███===░███=====░███========███=====░░███░███====░░░==░███==█=░=░███");
                System.out.println("==░░█████===░███======░███=░███===░███=====░███=======░███======░███░░█████████==░██████===░███");
                System.out.println("===░░███====░███======░███=░███===░███=====░███=======░███======░███=░░░░░░░░███=░███░░█===░███");
                System.out.println("====░███====░░███=====███==░███===░███=====░███======█░░███=====███==███====░███=░███=░===█░░░=");
                System.out.println("====█████====░░░███████░===░░████████======███████████=░░░███████░==░░█████████==██████████=███");
                System.out.println("===░░░░░=======░░░░░░░======░░░░░░░░======░░░░░░░░░░░====░░░░░░░=====░░░░░░░░░==░░░░░░░░░░=░░░=");
                System.out.println();

            } else {
                System.out.println();
                System.out.println("=███████████==█████=========█████████===█████=█████=██████████=███████████=======████████===");
                System.out.println("░░███░░░░░███░░███=========███░░░░░███=░░███=░░███=░░███░░░░░█░░███░░░░░███=====███░░░░███==");
                System.out.println("=░███====░███=░███========░███====░███==░░███=███===░███==█=░==░███====░███====░░░====░███==");
                System.out.println("=░██████████==░███========░███████████===░░█████====░██████====░██████████========███████===");
                System.out.println("=░███░░░░░░===░███========░███░░░░░███====░░███=====░███░░█====░███░░░░░███======███░░░░====");
                System.out.println("=░███=========░███======█=░███====░███=====░███=====░███=░===█=░███====░███=====███======█==");
                System.out.println("=█████========███████████=█████===█████====█████====██████████=█████===█████===░██████████==");
                System.out.println("░░░░░========░░░░░░░░░░░=░░░░░===░░░░░====░░░░░====░░░░░░░░░░=░░░░░===░░░░░====░░░░░░░░░░===");
                System.out.println("============================================================================================");
                System.out.println("============================================================================================");
                System.out.println("=█████===███===█████=█████=██████===█████==█████████=====███================================");
                System.out.println("░░███===░███==░░███=░░███=░░██████=░░███==███░░░░░███===░███================================");
                System.out.println("=░███===░███===░███==░███==░███░███=░███=░███====░░░====░███================================");
                System.out.println("=░███===░███===░███==░███==░███░░███░███=░░█████████====░███================================");
                System.out.println("=░░███==█████==███===░███==░███=░░██████==░░░░░░░░███===░███================================");
                System.out.println("==░░░█████░█████░====░███==░███==░░█████==███====░███===░░░=================================");
                System.out.println("====░░███=░░███======█████=█████==░░█████░░█████████=====███================================");
                System.out.println("=====░░░===░░░======░░░░░=░░░░░====░░░░░==░░░░░░░░░=====░░░=================================");
                System.out.println();
            }
        }
    }
    
    private static void resetHeroStats(Hero hero) {
        hero.setHp(hero.getMaxHp());
        hero.setMana(hero.getMaxMana());
        if (hero instanceof BlademasterKaito) {
            ((BlademasterKaito) hero).resetPP(); 
        }
       
        if (hero instanceof PyromancerSelena) {
            ((PyromancerSelena) hero).resetPP(); 
        }
        if (hero instanceof NecromancerEzekiel) {
            ((NecromancerEzekiel) hero).resetPP(); 
        }
        if (hero instanceof PaladinLionhart) {
            ((PaladinLionhart) hero).resetPP(); 
        }
        if (hero instanceof ShadowAssassinLira) {
            ((ShadowAssassinLira) hero).resetPP(); 
        }
        if (hero instanceof WarriorBiba) {
            ((WarriorBiba) hero).resetPP(); 
        }
    }

    private static boolean askToReplay(Scanner scanner) {
    String response = "";
   
    while (true) {
        try {
            System.out.print("Do you want to play again? (yes/no): ");
            response = scanner.next();  

            if (response.equalsIgnoreCase("yes")) {
                return true;
            } else if (response.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            scanner.nextLine();
            }
        }
    }
}
