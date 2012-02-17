package tests.mocks;

import files.CodeFileWriter;

public class MockFileWriter extends CodeFileWriter {

    private String writtenData = "";

    public MockFileWriter() {
	super(null);
    }

    @Override
    public void write(String str) {
	writtenData = writtenData + str;
    }

    public String getWrittenData() {
	return writtenData;
    }
}
