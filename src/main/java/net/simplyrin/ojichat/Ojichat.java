package net.simplyrin.ojichat;

import java.util.concurrent.Callable;

import net.simplyrin.ojichat.generator.OjichatConfig;
import net.simplyrin.ojichat.generator.OjichatGenerator;
import picocli.CommandLine;

/**
 * Created by SimplyRin on 2025/09/13.
 * 
 * Copyright (c) 2019 Yamada, Yasuhiro
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
@CommandLine.Command(
    name = "ojichat",
    description = "Ojisan Nanchatte (ojichat) command version 0.2.2-java",
    version = "Ojisan Nanchatte (ojichat) command version 0.2.2-java\n" +
              "Copyright (c) 2020 Yamada, Yasuhiro\n" +
              "Released under the MIT License.\n" +
              "https://github.com/greymd/ojichat"
)
public class Ojichat implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", arity = "0..1", description = "Name")
    private String name = "";

    @CommandLine.Option(names = {"-e", "--emoji"}, description = "絵文字/顔文字の最大連続数 [default: 4]")
    private int emoji = 4;

    @CommandLine.Option(names = {"-p", "--punctuation"}, description = "句読点挿入頻度レベル [min:0, max:3] [default: 0]")
    private int punctuation = 0;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "ヘルプを表示")
    private boolean help;

    @CommandLine.Option(names = {"-V", "--version"}, versionHelp = true, description = "バージョンを表示")
    private boolean version;

    @Override
    public Integer call() throws Exception {
        OjichatConfig config = new OjichatConfig();
        config.setTargetName(name);
        config.setEmojiNum(emoji);
        config.setPunctuationLevel(punctuation);

        String result = OjichatGenerator.start(config);
        System.out.println(result);
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Ojichat()).execute(args);
        System.exit(exitCode);
    }

}
