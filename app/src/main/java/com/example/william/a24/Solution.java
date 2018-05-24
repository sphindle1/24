package com.example.william.a24;

public class Solution {
    public static String solve(String one, String two, String three, String four) {
        return helper3(one, two, three, four, 4, 1);
    }
    private static String helper(String eq1, String eq2) {
        int a = eqsolve(eq1);
        int b = eqsolve(eq2);
        if (a * b == 24) {
            return eq1 + "*" + eq2;
        } else if (b != 0 && a % b == 0 && a / b == 24) {
            return eq1 + "/" + eq2;
        } else if (a != 0 && b % a == 0 && b / a == 24) {
            return eq2 + "/" + eq1;
        } else if (a + b == 24) {
            return eq1 + "+" + eq2;
        } else if (a - b == 24) {
            return eq1 + "-" + eq2;
        } else if (b - a == 24) {
            return eq2 + "-" + eq1;
        }
        return "IMPOSSIBLE";
    }
    private static String helper2(String eq1, String eq2, String eq3, int count) {
        if (count == 0) {
            return "IMPOSSIBLE";
        }
        String neweq = "(" + eq1 + "*" + eq2 + ")";
        if (!helper(neweq, eq3).equals("IMPOSSIBLE")) {
            return helper(neweq, eq3);
        }
        neweq = "(" + eq1 + "+" + eq2 + ")";
        if (!helper(neweq, eq3).equals("IMPOSSIBLE")) {
            return helper(neweq, eq3);
        }
        if (eqsolve(eq1) > eqsolve(eq2)) {
            neweq = "(" + eq1 + "-" + eq2 + ")";
        } else {
            neweq = "(" + eq2 + "-" + eq1 + ")";
        }
        if (!helper(neweq, eq3).equals("IMPOSSIBLE")) {
            return helper(neweq, eq3);
        }
        if (eqsolve(eq1) > eqsolve(eq2)) {
            neweq = "(" + eq1 + "/" + eq2 + ")";
        } else {
            neweq = "(" + eq2 + "/" + eq1 + ")";
        }
        if (!helper(neweq, eq3).equals("IMPOSSIBLE")) {
            return helper(neweq, eq3);
        }
        return helper2(eq2, eq3, eq1, count-1);
    }
    private static String helper3(String eq1, String eq2, String eq3, String eq4, int count, int track) {
        if (count == 0) {
            return "IMPOSSIBLE";
        }
        String neweq = "(" + eq1 + "*" + eq2 + ")";
        String result = "";
        result = helper2(neweq, eq3, eq4, 3);
        if (!result.equals("IMPOSSIBLE")) {
            return result;
        }
        neweq = "(" + eq1 + "+" + eq2 + ")";
        result = helper2(neweq, eq3, eq4, 3);
        if (!result.equals("IMPOSSIBLE")) {
            return result;
        }
        if (Integer.parseInt(eq1) > Integer.parseInt(eq2)) {
            neweq = "(" + eq1 + "-" + eq2 + ")";
        } else {
            neweq = "(" + eq2 + "-" + eq1 + ")";
        }
        result = helper2(neweq, eq3, eq4, 3);
        if (!result.equals("IMPOSSIBLE")) {
            return result;
        }
        if (Integer.parseInt(eq1) > Integer.parseInt(eq2)) {
            neweq = "(" + eq1 + "/" + eq2 + ")";
        } else {
            neweq = "(" + eq2 + "/" + eq1 + ")";
        }		result = helper2(neweq, eq3, eq4, 3);
        if (!result.equals("IMPOSSIBLE")) {
            return result;
        }
        String ans = "IMPOSSIBLE";
        if (track == 2 || track == 1) {
            ans = helper3(eq2, eq4, eq1, eq3, count - 1, 2);
        }
        if (ans == "IMPOSSIBLE" && (track == 3 || track == 1)) {
            ans = helper3(eq2, eq3, eq4, eq1, count - 1, 3);
        }
        return ans;
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
}
