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
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PascalSyntaxManager extends LangSyntaxManager implements SyntaxManager {
    private static final Pattern PATTERN_KEYWORDS =
            Pattern.compile("\\b(and|array|asm|begin|break|case|" +
                    "const|constructor|continue|destructor|div|do|downto|else|end|" +
                    "false|file|for|function|goto|if|implementation|in|inline|" +
                    "label|interface|mod|nil|not|object|of|on|operator|" +
                    "or|packed|procedure|program|record|repeat|set|shl|shr|" +
                    "string|then|to|true|type|unit|until|uses|var|" +
                    "while|with|xor)\\b");

    //Brackets and Colons
    private static final Pattern PATTERN_BUILTINS = Pattern.compile("\\,|\\:|\\;|\\(|\\)|\\[|\\]");

    //Data
    private static final Pattern PATTERN_NUMBERS = Pattern.compile("\\b(\\d*[.]?\\d+)\\b");
    private static final Pattern PATTERN_STRING = Pattern.compile("'.*'");
    private static final Pattern PATTERN_COMMENTS = Pattern.compile("(//.*?$)|(\\(\\*.*?\\*\\))|(\\{.*?\\})",
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
        code.setPairCompleteMap(pairCompleteMap);
    }

    //Snippets
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static void addSnippetsAndKeywords(Context context, CodeView code){
        code.setThreshold(2);
        String packageTitle, packagePrefix, packageBody;

        List<Code> codes = new ArrayList<>();

        addKeywords(context, codes, R.array.PascalKeywords);

        packageTitle = "Begin End;";
        packagePrefix = "begin;";
        packageBody = "begin end;";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Begin End.";
        packagePrefix = "begin.";
        packageBody = "begin end.";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Switch Case";
        packagePrefix = "switch";
        packageBody = "case (expression) of\n" +
                "    L1 : S1;\n" +
                "    L2: S2;\n" +
                "end;";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Write";
        packagePrefix = "write";
        packageBody = "write();";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Writeln";
        packagePrefix = "writeln";
        packageBody = "writeln();";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Read";
        packagePrefix = "read";
        packageBody = "read();";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Readln";
        packagePrefix = "readln";
        packageBody = "readln()";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        CodeViewAdapter codeAdapter = new CodeViewAdapter(context, R.layout.list_item_suggestion,
                R.id.suggestItemTextView, codes);
        code.setAdapter(codeAdapter);
    }

    //Applies patterns
    private static void applyPatterns(Context context, CodeView code){
        code.setUpdateDelayTime(100);
        code.setTextColor(context.getResources().getColor(R.color.mainCode));
        code.addSyntaxPattern(PATTERN_KEYWORDS, context.getResources().getColor(R.color.keyWordsOrange));
        code.addSyntaxPattern(PATTERN_NUMBERS, context.getResources().getColor(R.color.numbersCode));
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
    }

    @Override
    public String getInitCode() {
        return "var\nbegin\n\nend.";
    }

    @Override
    public String getLanguage() {
        return "pascal";
    }

    @Override
    public String getVersionIndex() {
        return "3";
    }
}
