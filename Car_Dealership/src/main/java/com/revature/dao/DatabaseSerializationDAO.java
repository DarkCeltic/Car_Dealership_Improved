package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.Log4jEntityResolver;

import com.revature.pojo.Database;

import jdk.internal.org.jline.utils.Log;

public class DatabaseSerializationDAO implements DatabaseDAO {
	private static Logger log = Logger.getRootLogger();

	@Override
	public void createDatabase(Database data) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream("Database.dat");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
			log.info("Database was serialized");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("File not found " + e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("IOException " + e);
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("IOException " + e);
			}
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("IOException " + e);
			}
		}

	}

	@Override
	public Database loadDatabase() {
		Database data = null;
		String filename;
		filename = "Database.dat";
		try (FileInputStream fis = new FileInputStream(filename); ObjectInputStream ois = new ObjectInputStream(fis);) {
			data = (Database) ois.readObject();
			log.info("Database was deserialized");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("File not found " + e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("IOException " + e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("ClassNotFoundException " + e);
		}
		return data;
	}

}
