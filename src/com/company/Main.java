package com.company;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.parseInt;

public class Main {

    static int gold = 60, trees = 15, unr_trees = 0, r_trees = 0, fertilizer = 1, animal_protection = 0;

    static int refinesLeftThisTurn;

    static int turn = 1, actionsUsedThisTurn = 0;

    static int wildlifeProtection;

    public static void main(String[] args) {
	    cls();
	    System.out.println("Treemnger:\n\n");
	    game();
    }

    public static void game() {
        boolean endCommand = false;
        Scanner s = new Scanner(System.in);

        while (true) {
            endCommand = false;
            refinesLeftThisTurn = 10;
            actionsUsedThisTurn = 0;
            System.out.println("Turn: " + turn);

            System.out.println("\n");
            while (!endCommand) {
                System.out.println("Enter a command (type 'help' for list)");
                String commandEntered = s.nextLine();

                String[] splitCommand = commandEntered.split(" ");

                String command = splitCommand[0];
                String[] subcommands = Arrays.copyOfRange(splitCommand, 1, commandEntered.length());

                switch (command.toLowerCase()) {
                    case "help":
                        System.out.println("Commands: 'help', 'resources', 'chop', 'sell', 'refine', 'buy', 'use', 'clear', 'end'");
                        endCommand = false;
                        break;

                    case "resources":
                        printResources();
                        endCommand = false;
                        break;

                    case "chop":
                        if (subcommands[0] != null) {
                            if (trees - parseInt(subcommands[0]) >= 0) {
                                trees -= parseInt(subcommands[0]);
                                unr_trees += parseInt(subcommands[0]);
                                if (rng(1, 30) == 1) {
                                    System.out.println("Lucky! You got 5 extra wood!");
                                    unr_trees += 5;
                                }

                            } else {
                                System.out.println("You need more trees!");
                                break;
                            }
                        } else {
                            System.out.println("Please provide number of trees to be cut, e.g. 'chop 3'.");
                            break;
                        }
                        endCommand = false;
                        actionsUsedThisTurn++;
                        break;

                    case "sell":
                        if (subcommands[0] == null) {
                            System.out.println("Please provide the type of wood to sell!");
                            break;
                        }
                        if (subcommands[1] == null || NotIntString(subcommands[1])) {
                            System.out.println("Please provide the amount of wood to sell!");
                            break;
                        }

                        switch (subcommands[0]) {
                            case "unrefined":
                                if (unr_trees - parseInt(subcommands[1]) >= 0) {
                                    unr_trees -= parseInt(subcommands[1]);
                                    gold += rng(4, 6) * parseInt(subcommands[1]);

                                } else {
                                    System.out.println("You need more unrefined logs!");
                                }
                                break;
                            case "refined":
                                if (r_trees - parseInt(subcommands[1]) >= 0) {
                                    r_trees -= parseInt(subcommands[1]);
                                    gold += rng(13, 17) * parseInt(subcommands[1]);
                                } else {
                                    System.out.println("You need more refined logs!");
                                }
                                break;
                            default:
                                System.out.println("Please specify either 'unrefined' or 'refined' logs.");
                                break;
                        }
                        actionsUsedThisTurn++;
                        break;

                    case "refine":
                        if (refinesLeftThisTurn < 0) {
                            System.out.println("You cannot refine any more times this turn! Wait till next turn to regain your 10 refines!");
                        }
                        if (subcommands[0] == null) {
                            System.out.println("Please provide the amount of wood to refine!");
                            break;
                        }

                        if (unr_trees - parseInt(subcommands[0]) >= 0 || gold - (parseInt(subcommands[0]) * 5) >= 0) {
                            unr_trees -= parseInt(subcommands[0]);
                            r_trees += parseInt(subcommands[0]);
                            gold -= parseInt(subcommands[0]) * 5;
                            refinesLeftThisTurn -= parseInt(subcommands[0]);
                        }
                        actionsUsedThisTurn++;
                        break;

                    case "buy":
                        if (subcommands[0] == null) {
                            System.out.println("Please provide what you would like to buy!");
                            break;
                        }
                        if (subcommands[1] == null || NotIntString(subcommands[1])) {
                            System.out.println("Please provide the amount you would like to buy!");
                            break;
                        }

                        switch (subcommands[0]) {
                            case "fertilizer":
                                if ((gold - parseInt(subcommands[1])) * 50 >= 0) {
                                    fertilizer += parseInt(subcommands[1]);
                                    gold -= parseInt(subcommands[1]) * 50;
                                    if (rng(1, 20) == 1) {
                                        System.out.println("Lucky! You got 2 extra fertilizer!");
                                        fertilizer += 2;
                                    }
                                }
                                break;
                            case "wildlifeprotection":
                                if ((gold - parseInt(subcommands[1])) * 50 >= 0) {
                                    animal_protection += parseInt(subcommands[1]);
                                    gold -= parseInt(subcommands[1]) * 40;
                                    break;
                                }

                            default:
                                System.out.println("Please specify either 'fertilizer' or 'wildlifeprotection'");
                                break;
                        }
                        actionsUsedThisTurn++;
                        break;

                    case "use":
                        if (subcommands[0] == null) {
                            System.out.println("Please provide what you would like to use!");
                            break;
                        }
                        if (subcommands[1] == null || NotIntString(subcommands[1])) {
                            System.out.println("Please provide how many of that item you would like to use!");
                            break;
                        }

                        switch (subcommands[0]) {
                            case "fertilizer":
                                if (fertilizer - parseInt(subcommands[1]) >= 0) {
                                    fertilizer -= parseInt(subcommands[1]);
                                    trees += rng(2, 4);
                                    if (rng(1, 10) == 1) {
                                        System.out.println("Lucky you! You get 5 extra trees!");
                                        trees += 5;
                                    }
                                } else {
                                    System.out.println("You need more fertilizer!");
                                }
                                break;
                            case "wildlifeprotection":
                                if (animal_protection - parseInt(subcommands[1]) >= 0) {
                                    animal_protection -= parseInt(subcommands[1]);
                                    wildlifeProtection++;
                                } else {
                                    System.out.println("You need more wildlife protection!");
                                }
                                break;
                            default:
                                System.out.println("Please specify either 'fertilizer' or 'wildlifeprotection'");
                        }
                        actionsUsedThisTurn++;
                        break;

                    case "clear":
                        cls();
                        break;

                    case "end":
                        cls();
                        System.out.println("Thank's for playing!");
                        System.exit(0);

                    default:
                        System.out.println("Invalid command!");
                        break;
                }

                if (actionsUsedThisTurn >= 5) {
                    turn++;
                    endCommand = true;
                }
            }
            if (wildlifeProtection == 0) {
                trees -= rng(2, 4);
            }
            if (trees < 0) {
                cls();
                System.out.println("You lost, making it to " + turn + " turns! Make it longer next time!");
                System.exit(0);
            }
        }
    }

    public static void cls() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printResources() {
        String s = "Trees: " +
                trees +
                "\nGold: " +
                gold +
                "\n\nUnrefined Trees: " +
                unr_trees +
                "\nRefined Trees: " +
                r_trees +
                "\n\nFertilizer: " +
                fertilizer +
                "\nAnimal Protection: " +
                animal_protection;
        System.out.println(s);
    }

    private static boolean NotIntString(String string) {
        try {
            parseInt(string);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private static int rng(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}