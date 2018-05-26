package com.example.william.a24;

import java.util.ArrayList;

public class Solution {
    public static ArrayList<String> solve(String one, String two, String three, String four) {
        ArrayList<String> unrefined = helper3(one, two, three, four, 4, 1, new ArrayList<String>());
        ArrayList<String> simplified = simplify(unrefined);
        ArrayList<String> refined = rearrange(simplified);
        ArrayList<String> reduced = reduce(refined);
        return reduced;
    }
    private static void helper(String eq1, String eq2, ArrayList<String> solutions) {
        int a = eqsolve(eq1);
        int b = eqsolve(eq2);
        if (a * b == 24) {
            solutions.add(eq1 + "*" + eq2);
        } else if (b != 0 && a % b == 0 && a / b == 24) {
            solutions.add(eq1 + "/" + eq2);
        } else if (a != 0 && b % a == 0 && b / a == 24) {
            solutions.add(eq2 + "/" + eq1);
        } else if (a + b == 24) {
            solutions.add(eq1 + "+" + eq2);
        } else if (a - b == 24) {
            solutions.add(eq1 + "-" + eq2);
        } else if (b - a == 24) {
            solutions.add(eq2 + "-" + eq1);
        }
    }
    private static ArrayList<String> helper2(String eq1, String eq2, String eq3, int count, ArrayList<String> solutions) {
        if (count == 0) {
            return solutions;
        }
        String neweq = "(" + eq1 + "*" + eq2 + ")";
        helper(neweq, eq3, solutions);
        neweq = "(" + eq1 + "+" + eq2 + ")";
        helper(neweq, eq3, solutions);
        if (eqsolve(eq1) > eqsolve(eq2)) {
            neweq = "(" + eq1 + "-" + eq2 + ")";
        } else {
            neweq = "(" + eq2 + "-" + eq1 + ")";
        }
        helper(neweq, eq3, solutions);
        if (eqsolve(eq1) > eqsolve(eq2)) {
            neweq = "(" + eq1 + "/" + eq2 + ")";
        } else {
            neweq = "(" + eq2 + "/" + eq1 + ")";
        }
        helper(neweq, eq3, solutions);
        return helper2(eq2, eq3, eq1, count-1, solutions);
    }
    private static ArrayList<String> helper3(String eq1, String eq2, String eq3, String eq4, int count, int track, ArrayList<String> solutions) {
        if (count == 0) {
            return solutions;
        }
        String neweq = "(" + eq1 + "*" + eq2 + ")";
        helper2(neweq, eq3, eq4, 3, solutions);
        neweq = "(" + eq1 + "+" + eq2 + ")";
        helper2(neweq, eq3, eq4, 3, solutions);
        if (Integer.parseInt(eq1) > Integer.parseInt(eq2)) {
            neweq = "(" + eq1 + "-" + eq2 + ")";
        } else {
            neweq = "(" + eq2 + "-" + eq1 + ")";
        }
        helper2(neweq, eq3, eq4, 3, solutions);
        if (Integer.parseInt(eq1) > Integer.parseInt(eq2)) {
            neweq = "(" + eq1 + "/" + eq2 + ")";
        } else {
            neweq = "(" + eq2 + "/" + eq1 + ")";
        }
        helper2(neweq, eq3, eq4, 3, solutions);
        if (track == 2 || track == 1) {
            helper3(eq2, eq4, eq1, eq3, count - 1, 2, solutions);
        }
        if (track == 3 || track == 1) {
            helper3(eq2, eq3, eq4, eq1, count - 1, 3, solutions);
        }
        return solutions;
    }
    private static int eqsolve(String equation) {
        String simplified = equation;
        int parenl = simplified.indexOf('(');
        while (parenl != -1) {
            int parenr = 0;
            int count = 0;
            for (int i = parenl + 1; i < equation.length(); i++) {
                if (simplified.charAt(i) == '(') {
                    count++;
                } else if (simplified.charAt(i) == ')') {
                    if (count == 0) {
                        parenr = i;
                        break;
                    }
                    count--;
                }
            }
            String sim = Integer.toString(eqsolve(simplified.substring(parenl + 1, parenr)));
            simplified = simplified.substring(0, parenl) + sim + simplified.substring(parenr + 1);
            parenl = simplified.indexOf('(');
        }
        return simpEqSolve(simplified);
    }
    private static int simpEqSolve(String equation) {
        char op;
        if (equation.indexOf('*') != -1) {
            op = '*';
        } else if (equation.indexOf('/') != -1) {
            op = '/';
        } else if (equation.indexOf('-') > 0) {
            op = '-';
        } else if (equation.indexOf('+') != -1) {
            op = '+';
        } else {
            return parse(equation);
        }
        int a = parse(equation.substring(0, equation.indexOf(op)));
        int b = parse(equation.substring(equation.indexOf(op) + 1));
        if (op == '+') {
            return a + b;
        } else if (op == '*') {
            return a * b;
        } else if (op == '-') {
            return a - b;
        } else if (b != 0 && a % b == 0){
            return a / b;
        } else {
            return 1000;
        }
    }
    private static int parse(String x) {
        if (x.contains("-")) {
            return -Integer.parseInt(x.substring(1));
        }
        return Integer.parseInt(x);
    }

    private static ArrayList<String> simplify(ArrayList<String> solutions) {
        ArrayList<String> simplified = new ArrayList<>();
        for(String s : solutions) {
            simplified.add(simplify(s));
        }
        return simplified;
    }
    private static String simplify(String eq) {
        if (eq.indexOf('(') == -1) {
            return eq;
        }
        String operators = "+-/*";
        int degree = 0;
        int parenl = -1;
        int parenr = -1;
        char inchar = '?';
        char outchar = '?';
        for (int i = 0; i < eq.length(); i++) {
            if (eq.charAt(i) == '(') {
                degree++;
                if (degree == 1) {
                    parenl = i;
                }
            } else if (eq.charAt(i) == ')') {
                if (degree == 1) {
                    parenr = i;
                    if (outchar != '?' && outchar != '/') {
                        String replacement = simplifyhelper(eq.substring(parenl, parenr + 1), inchar, outchar);
                        eq = eq.substring(0, parenl) + replacement + eq.substring(parenr + 1);
                    }
                }
                degree--;
            } else if (degree == 1 && operators.indexOf(eq.charAt(i)) != -1) {
                inchar = eq.charAt(i);
            } else if (degree == 0 && operators.indexOf(eq.charAt(i)) != -1) {
                outchar = eq.charAt(i);
                if (parenl != -1) {
                    String replacement;
                    if (outchar == '-') {
                        replacement = simplifyhelper(eq.substring(parenl, parenr + 1), inchar, '+');
                    } else {
                        replacement = simplifyhelper(eq.substring(parenl, parenr + 1), inchar, outchar);
                    }
                    int len = eq.length();
                    eq = eq.substring(0, parenl) + replacement + eq.substring(parenr + 1);
                    if (eq.length() < len) {
                        i -= 2;
                    }
                }
            }
        }
        return eq;
    }
    private static String simplifyhelper(String eq, char insign, char outsign) {
        if ((outsign == '-' || outsign == '+') && (insign == '-' || insign == '+')) {
            if (outsign == '-') {
                if (eq.indexOf('-') != -1) {
                    eq = eq.replace('-', '+');
                } else {
                    eq = eq.replace('+', '-');
                }
            }
            return simplify(eq.substring(1, eq.length() - 1));
        }
        if ((outsign == '*' || outsign == '/') && (insign == '*' || insign == '/')) {
            return simplify(eq.substring(1, eq.length() - 1));
        }
        return "(" + simplify(eq.substring(1, eq.length() - 1)) + ")";
    }
    private static ArrayList<String> rearrange(ArrayList<String> solutions) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : solutions) {
            result.add(rearrange(s));
        }
        return result;
    }
    private static String rearrange(String eq) {
        ArrayList<String> parts = new ArrayList<>();
        int divider = 0;
        int degree = 0;
        String op = "+-/*";
        int parenl = -1;
        for (int i = 0; i < eq.length(); i++) {
            if (eq.charAt(i) == '(') {
                degree ++;
                if (degree == 1) {
                    parenl = i;
                }
            } else if (eq.charAt(i) == ')') {
                degree --;
                if (degree == 0) {
                    String replacement = rearrange(eq.substring(parenl + 1, i));
                    eq = eq.substring(0, parenl) + "(" + replacement + ")" + eq.substring(i + 1);
                }
            } else if (op.indexOf(eq.charAt(i)) != -1 && degree == 0) {
                if (parts.size() == 0) {
                    String first = eq.substring(0, i);
                    if (eq.charAt(i) == '+' || eq.charAt(i) == '-') {
                        first = "+" + first;
                    } else {
                        first = "*" + first;
                    }
                    parts.add(first);
                } else {
                    parts.add(eq.substring(divider, i));
                }
                divider = i;
            }
        }
        parts.add(eq.substring(divider));
        return order(parts);
    }
    private static String order(ArrayList<String> parts) {
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> last = new ArrayList<>();
        for (String s : parts) {
            if (s.charAt(0) == '+' || s.charAt(0) == '*') {
                first.add(s);
            } else {
                last.add(s);
            }
        }
        String result = order2(first) + order2(last);
        return result.substring(1);
    }
    private static String order2(ArrayList<String> parts) {
        if (parts.size() == 0) {
            return "";
        }
        ArrayList<String> ordered = new ArrayList<>();
        while (parts.size() > 0) {
            String s = parts.remove(0);
            boolean added = false;
            for (int i = 0; i < ordered.size(); i++) {
                if (compare(ordered.get(i), s) > 0) {
                    ordered.add(i, s);
                    added = true;
                    break;
                }
            }
            if (!added) {
                ordered.add(s);
            }
        }
        String eq = "";
        for (int i = 0; i < ordered.size(); i++) {
            eq = eq + ordered.get(i);
        }
        return eq;
    }
    private static int compare(String str1, String str2) {
        if (str1.equals(str2)) {
            return 0;
        } else if (str1.indexOf('(') != -1) {
            if (str2.indexOf('(') != -1) {
                return str1.compareTo(str2);
            } else {
                return -1;
            }
        } else if (str2.indexOf('(') != -1) {
            return 1;
        } else {
            return str1.compareTo(str2);
        }
    }
    private static ArrayList<String> reduce(ArrayList<String> solutions) {
        ArrayList<String> reduced = new ArrayList<>();
        while (solutions.size() > 0) {
            String s = solutions.remove(0);
            if (!reduced.contains(s)) {
                reduced.add(s);
            }
        }
        return reduced;
    }
}
