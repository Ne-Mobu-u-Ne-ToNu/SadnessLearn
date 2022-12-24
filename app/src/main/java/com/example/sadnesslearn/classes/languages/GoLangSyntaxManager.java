package com.example.sadnesslearn.classes.languages;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.amrdeveloper.codeview.Code;
import com.amrdeveloper.codeview.CodeView;
import com.amrdeveloper.codeview.CodeViewAdapter;
import com.amrdeveloper.codeview.Snippet;
import com.example.sadnesslearn.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class GoLangSyntaxManager extends LangSyntaxManager implements SyntaxManager {
    private static final Pattern PATTERN_KEYWORDS =
            Pattern.compile("\\b(break|default|func|interface|case|defer|" +
                    "go|map|struct|chan|else|goto|package|switch|const" +
                    "|fallthrough|if|range|type|continue|for|import|return|var|" +
                    "string|true|false|new|nil|byte|bool|int|int8|int16|int32|int64)\\b");

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

        addKeywords(context, codes, R.array.GoLangKeyword);

        packageTitle = "Main Method";
        packagePrefix = "main";
        packageBody = "func main() {}";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Switch Case";
        packagePrefix = "switch";
        packageBody = "switch variable {\n" +
                "    case val:\n" +
                "    default:\n" +
                "}";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "fmt.Print";
        packagePrefix = "print";
        packageBody = "fmt.Print()";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "fmt.Println";
        packagePrefix = "println";
        packageBody = "fmt.Println()";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "fmt.Printf";
        packagePrefix = "printf";
        packageBody = "fmt.Printf()";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        CodeViewAdapter codeAdapter = new CodeViewAdapter(context, R.layout.list_item_suggestion,
                R.id.suggestItemTextView, codes);
        code.setAdapter(codeAdapter);
    }

    //Enables auto indentation
    private static void enableIndentation(CodeView code){
        code.setTabLength(4);

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
    public void applyCodeTheme(Context context, CodeView code) {
        applyOtherFeatures(context, code);
        applyPatterns(context, code);
        addPairs(code);
        addSnippetsAndKeywords(context, code);
        enableIndentation(code);
    }

    @Override
    public String getInitCode() {
        return "package main\n" +
                "\n" +
                "import \"fmt\"\n" +
                "\n" +
                "func main() {\n" +
                "    \n" +
                "}";
    }

    @Override
    public String getLanguage() {
        return "go";
    }

    @Override
    public String getVersionIndex() {
        return "4";
    }
}
