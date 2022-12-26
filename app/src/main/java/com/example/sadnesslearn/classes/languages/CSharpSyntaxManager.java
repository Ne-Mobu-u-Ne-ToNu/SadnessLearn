package com.example.sadnesslearn.classes.languages;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.amrdeveloper.codeview.Code;
import com.amrdeveloper.codeview.CodeView;
import com.amrdeveloper.codeview.CodeViewAdapter;
import com.amrdeveloper.codeview.Keyword;
import com.amrdeveloper.codeview.Snippet;
import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class CSharpSyntaxManager extends LangSyntaxManager implements SyntaxManager {
    //Language Keywords
    private static final Pattern PATTERN_KEYWORDS =
            Pattern.compile("\\b(abstract|as|base|bool|break|" +
                    "byte|case|catch|char|checked|" +
                    "class|const|continue|decimal|default|" +
                    "delegate|do|double|else|enum|" +
                    "event|explicit|extern|false|finally|" +
                    "fixed|float|for|foreach|goto|" +
                    "if|implicit|in|int|interface|" +
                    "internal|is|lock|long|namespace|" +
                    "new|null|object|operator|out|" +
                    "override|params|private|protected|public|" +
                    "readonly|ref|return|sbyte|sealed|" +
                    "short|sizeof|stackalloc|static|string|" +
                    "struct|switch|this|throw|true|" +
                    "try|typeof|uint|ulong|unchecked|" +
                    "unsafe|ushort|using|var|virtual|" +
                    "void|volatile|while)\\b");

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

        addKeywords(context, codes, R.array.CSharpKeywords);

        packageTitle = "Main Method";
        packagePrefix = "main";
        packageBody = "static void Main() {}";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Switch Case";
        packagePrefix = "switch";
        packageBody = "switch (variable)\n{\n" +
                "    case value:\n" +
                "        break;\n" +
                "    default:\n" +
                "        break;\n}";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Try Catch";
        packagePrefix = "try";
        packageBody = "try \n{\n" +
                "    \n" +
                "}\n" +
                "catch (Exception e) {}";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Console.Write()";
        packagePrefix = "write";
        packageBody = "Console.Write();";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Console.WriteLine()";
        packagePrefix = "writeln";
        packageBody = "Console.WriteLine();";
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
        code.addSyntaxPattern(PATTERN_KEYWORDS, context.getResources().getColor(R.color.keyWordsBlue));
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
        return Constants.CSHARP_INIT_CODE;
    }

    @Override
    public String getLanguage(){
        return Constants.CSHARP_LANG_NAME;
    }

    @Override
    public String getVersionIndex() {
        return Constants.CSHARP_LANG_VERSION;
    }
}
