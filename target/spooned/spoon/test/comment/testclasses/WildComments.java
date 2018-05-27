package spoon.test.comment.testclasses;


public class WildComments {
    java.lang.String[] comments = new java.lang.String[]{ "/* test single line comments with nested * and */ and **/", "* starts with *", "/* starts with /*", "*/ starts with */", "*/ starts with space and */", "/ starts with /", "// starts with //", "test wild multiline comments", "/ starts with /", "/ starts with space and /", "// starts with //", "// starts with space and //", "/* starts with /*", "/* second line starts with /*", "/* second line starts with /* and ends with /*\n/*", "trim all empty lines?", "trim all empty lines,\nbut not in the middle\n\n\nof the comment!\n this line keeps prefix and trailing space \n\tbut it was not last line, whose trailing space is ignored", "// 1 second line starts with //", "// 2 second line starts with //", "// 3 second line starts with //", "// 4 second line starts with //", "", "", "*", "**", "", "", "*", "", "", "", "test wild javadoc comments", "/ starts with space and /", "// starts with space and //", "/* starts with space and /*", "* starts and ends with 3 * **", "** starts and ends with 4 * ***" };
}

