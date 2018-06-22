package org.molgenis.app.manager.exception;

import org.molgenis.i18n.CodedRuntimeException;

import static java.util.Objects.requireNonNull;

public class CouldNotUploadAppException extends CodedRuntimeException
{
	private static final String ERROR_CODE = "AM09";
	private final String fileName;

	public CouldNotUploadAppException(String fileName)
	{
		super(ERROR_CODE);
		this.fileName = requireNonNull(fileName);
	}

	@Override
	protected Object[] getLocalizedMessageArguments()
	{
		return new Object[] { fileName };
	}

	@Override
	public String getMessage()
	{
		return fileName;
	}

}
