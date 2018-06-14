package kmworks.dsltools.parser.base;


import xtc.parser.Result;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class Parsing {

    private AbstractParser parser;

    private Parsing(Reader reader, String srcFileName, Long srcLength) {

    }

    public Result parse() throws IOException {
        return checkNotNull(parser).parse();
    }

    public static class Builder {

        private Map<String, String> syntaxModules = new HashMap<>();
        private Charset charSet = StandardCharsets.UTF_8;
        private File srcFile;
        private long size;
        private Reader reader;

        public Builder addSyntaxModule(String syntaxModule, String syntaxType) {
            syntaxModules.put(syntaxModule, syntaxType);
            return this;
        }

        public Builder setReader(Reader reader) {
            this.srcFile = null;
            this.reader = reader;
            return this;
        }

        public Builder setSourceFile(File srcFile, Charset charset) {
            if (checkNotNull(srcFile).exists() && srcFile.canRead()) {
                closeReader();
                this.srcFile = srcFile;
                this.size = srcFile.length();
                this.charSet = charSet;
            } else {
                throw new IllegalArgumentException("source-file not accessible: " + srcFile.getAbsolutePath());
            }
            return this;
        }

        public Parsing build() {

            // Validate property state: either reader or srcFile must have been set
            // If both are set srcFile will take precedence
            if (reader == null && srcFile == null) {
                throw new RuntimeException("Either the reader or the srcFile must be defined.");
            }

            if (reader == null) {   // use srcFile
                try {
                    reader = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(srcFile), charSet));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            // TODO: return new arser instance ....
        }

        private void closeReader() {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ex) {
                } finally {
                    reader = null;
                }
            }
        }

    }

}
