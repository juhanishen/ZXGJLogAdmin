package com.zxgj.logadmin.server.remote.console.linuxmanag;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Helper {

	public static String getPath() throws IOException {
		String path="";
		BufferedReader br = new BufferedReader(new FileReader("lib/linux.property"));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        if (line != null) {
	            String[] attrs = line.split("=");
	            path=attrs[1];
	        }
	       
	    } finally {
	        br.close();
	    }
		return path;
	}

}
