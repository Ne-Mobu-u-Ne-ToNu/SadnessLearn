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

public class CPPSyntaxManager extends LangSyntaxManager implements SyntaxManager{
    //Language Keywords
    private static final Pattern PATTERN_KEYWORDS =
            Pattern.compile("\\b(alignas|alignof|and|and_eq|asm|atomic_cancel|atomic_commit|atomic_noexcept|auto|" +
                    "bitand|bitor|bool|break|case|catch|char|char8_t|char16_t|char32_t|class|compl|concept|" +
                    "const|consteval|constexpr|constinit|const_cast|continue|co_await|co_return|co_yield|decltype|default" +
                    "delete|do|double|dynamic_cast|else|enum|explicit|export|extern|false|float|for|friend|goto|if|" +
                    "inline|int|long|mutable|namespace|new|noexcept|not|not_eq|nullptr|operator|or|or_eq|private|" +
                    "protected|public|reflexpr|register|reinterpret_cast|requires|return|short|signed|sizeof|static|static_assert|" +
                    "static_cast|struct|switch|synchronized|template|this|thread_local|throw|true|try|typedef|typeid|typename|" +
                    "union|unsigned|using|virtual|void|volatile|wchar_t|while|xor|xor_eq)\\b");

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

        addKeywords(context, codes, R.array.CPPKeywords);

        packageTitle = "Main Method";
        packagePrefix = "main";
        packageBody = "int main() {}";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Switch Case";
        packagePrefix = "switch";
        packageBody = "switch (variable) {\n" +
                "    case value:\n" +
                "        break;\n" +
                "    default:\n" +
                "        break;\n}";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "Try Catch";
        packagePrefix = "try";
        packageBody = "try {\n" +
                "    \n" +
                "}\n" +
                "catch (ExceptionName e) {}";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "cout <<";
        packagePrefix = "cout";
        packageBody = "cout << ;";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "cout endl";
        packagePrefix = "coutln";
        packageBody = "cout << << endl;";
        codes.add(new Snippet(packageTitle, packagePrefix, packageBody));

        packageTitle = "cin >>";
        packagePrefix = "cin";
        packageBody = "cin >> ;";
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
        return "#include <iostream>\n" +
                "\n" +
                "using namespace std;\n" +
                "\n" +
                "int main() {\n" +
                "    \n" +
                "}";
    }

    @Override
    public String getLanguage() {
        return "cpp17";
    }

    @Override
    public String getVersionIndex() {
        return "1";
    }
}
