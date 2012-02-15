package tests.mocks;

import files.CodeFileWriter;

public class MockFileWriter extends CodeFileWriter {

    private String writtenData = new String();

    public MockFileWriter() {
	super(null);
    }

    public void write(String str) {
	writtenData = writtenData + str;
    }

    public String getWrittenData() {
	return writtenData;
    }
}
