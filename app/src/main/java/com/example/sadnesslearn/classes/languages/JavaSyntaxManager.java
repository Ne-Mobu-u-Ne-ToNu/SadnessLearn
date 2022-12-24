package com.example.sadnesslearn.classes.languages;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.amrdeveloper.codeview.Code;
import com.amrdeveloper.codeview.*;
import com.example.sadnesslearn.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class JavaSyntaxManager extends LangSyntaxManager implements SyntaxManager {
    //Language Keywords
    private static final Pattern PATTERN_KEYWORDS =
            Pattern.compile("\\b(abstract|assert|boolean|break|byte|case|" +
                    "catch|char|class|const|continue|default|do|double|else|" +
                    "enum|extends|final|finally|float|for|goto|if|implements|" +
                    "import|instanceof|int|interface|long|native|new|package|" +
                    "private|protected|public|return|short|static|strictfp|super|" +
                    "switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\\b");

    //Brackets and Colons
    private static final Pattern PATTERN_BUILTINS = Pattern.compile("\\,|\\:|\\;|\\(|\\)|(-(?=>)>)|\\{|\\}|\\[|\\]");

    //Data
    private static final Pattern PATTERN_NUMBERS = Pattern.compile("\\b(\\d*[.]?\\d+)\\b");
    private static final Pattern PATTERN_CHAR = Pattern.compile("'[a-zA-Z]'");
    private static final Pattern PATTERN_STRING = Pattern.compile("\".*\"");
    private static final Pattern PATTERN_COMMENTS = Pattern.compile("(//.*?$)|(/\\*.*?\\*/)",
            Pattern.MULTILINE | Pattern.DOTALL);

    //Pairs
    private static void addPairs(CodeView code){
        code.clearPairCompleteMap();
        code.enablePairComplete(true);
        code.enablePairCompleteCenterCursor(true);
        Map<Character, Character> pairCompleteMap = new HashMap<>();
        pairCompleteMap.put('{', '}');
        pairCompleteMap.put('[', ']');
        pairCompleteMap.put('(', ')');
        pairCompleteMap.put('\'', '\'');
        pairCompleteMap.put('"', '"');
        pairCompleteMap.put('<', '>');
        code.setPairCompleteMap(pairCompleteMap);
    }

    //Snippets
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static void addSnippetsAndKeywords(Context context, CodeView code){
        code.setThreshold(2);
        String packageTitle, packagePrefix, packageBody;

        List<Code> codes = new ArrayList<>();

        addKeywords(context, codes, R.array.java_keywords);

        packageTitle = "Main Method";
        packagePrefix = "main";
        packageBody = "public static void main(String[] args) {}";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Switch Case";
        packagePrefix = "switch";
        packageBody = "switch (variable) {\n" +
                "    case value:\n" +
                "        break;\n" +
                "    default:\n" +
                "        break;}";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Try Catch";
        packagePrefix = "try";
        packageBody = "try {\n" +
                "    \n" +
                "}\n" +
                "catch (Exception e) {}";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "System.out.print()";
        packagePrefix = "sout";
        packageBody = "System.out.print();";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "System.out.println()";
        packagePrefix = "soutln";
        packageBody = "System.out.println();";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "System.out.printf()";
        packagePrefix = "soutf";
        packageBody = "System.out.printf();";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        CodeViewAdapter codeAdapter = new CodeViewAdapter(context, R.layout.list_item_suggestion,
                R.id.suggestItemTextView, codes);
        code.setAdapter(codeAdapter);
    }

    //Enables auto indentation
    private static void enableIndentation(CodeView code){
        code.setTabLength(4);

        Set<String> inSt = new HashSet<>();
        Set<Character> indentationStart = new HashSet<>();
        indentationStart.add('{');
        code.setIndentationStarts(indentationStart);

        Set<Character> indentationEnds = new HashSet<>();
        indentationEnds.add('}');
        code.setIndentationEnds(indentationEnds);

        code.setEnableAutoIndentation(true);
    }

    //Applies patterns
    private static void applyPatterns(Context context, CodeView code){
        code.setUpdateDelayTime(100);
        code.setTextColor(context.getResources().getColor(R.color.mainCode));
        code.addSyntaxPattern(PATTERN_KEYWORDS, context.getResources().getColor(R.color.keyWordsOrange));
        code.addSyntaxPattern(PATTERN_NUMBERS, context.getResources().getColor(R.color.numbersCode));
        code.addSyntaxPattern(PATTERN_CHAR, context.getResources().getColor(R.color.stringCode));
        code.addSyntaxPattern(PATTERN_STRING, context.getResources().getColor(R.color.stringCode));
        code.addSyntaxPattern(PATTERN_COMMENTS, context.getResources().getColor(R.color.commentsCode));
        code.addSyntaxPattern(PATTERN_BUILTINS, context.getResources().getColor(R.color.bracketsCode));
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void applyCodeTheme(Context context, CodeView code){
        applyOtherFeatures(context, code);
        applyPatterns(context, code);
        addPairs(code);
        addSnippetsAndKeywords(context, code);
        enableIndentation(code);
    }

    @Override
    public String getInitCode(){
        //initial code
        return "public class Main {\n" +
                    "    public static void main(String[] args) {\n        \n    }\n}";
    }

    @Override
    public String getLanguage(){
        return "java";
    }

    @Override
    public String getVersionIndex() {
        return "4";
    }
}
