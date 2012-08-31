import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;


public class hardDriveManager {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Scanner input = new Scanner(System.in);
		LinkedList<File> fileList = getFileList(new File(/*input.nextLine()*/"C:\\Users\\Leon\\Documents"));
		//for(File f : fileList)
			//System.out.println(f.getName());
		System.out.println("number of files: " + fileList.size());
		//LinkedList<String> extensionList = getExtensionList(fileList);
		//Collections.sort(extensionList);
		//for(String s : extensionList)
			//System.out.println(s);
		//System.out.println("Number of types of files: " + extensionList.size());
		
		for(File f : fileList){
			//System.out.println(parseExtension(f) + "  " + (parseExtension(f).compareTo(".mp3")==0));
			if(parseExtension(f).compareTo(".doc")==0 || parseExtension(f).compareTo(".docx")==0 || parseExtension(f).compareTo(".txt")==0)
				if(!moveDoc(f,new File(/*input.nextLine()*/"G:\\Documents")))
					System.out.println(f.getName());
		}
		cleanUp(new File(/*input.nextLine()*/"C:\\Users\\Leon\\Documents"));
	}
	public static LinkedList<File> getFileList(File base){
		LinkedList<File> fileList = new LinkedList<File>();
		if(base.isDirectory())
			for(File f : base.listFiles())
				if(f.isFile())
					fileList.add(f);
				else if(f.isDirectory())
					try{
						for(File fl : getFileList(f))							
							fileList.add(fl);
					}
					catch(java.lang.NullPointerException e){
						System.out.println("null pointer: " + f.getName()+ " + " + f.getAbsolutePath());
					}
		return fileList;
	}
	public static LinkedList<String> getExtensionList(LinkedList<File> fileList) {
		LinkedList<String> extensionList = new LinkedList<String>();
		boolean collision;
		for(File f : fileList){
			collision = false;
			String fEx = parseExtension(f);
			for(String s : extensionList)
				if(fEx.compareTo(s)==0)
					collision = true;
			if(!collision)
				extensionList.add(fEx);
		}
		return extensionList;			
	}
	public static String parseExtension(File file){
		String text = "";
		
		try{
			//System.out.println(file.getName() + "   " + file.getName().lastIndexOf(".") + "    " + file.getName().substring(file.getName().lastIndexOf(".")));

			text = file.getName().substring(file.getName().lastIndexOf("."));
		}
		catch (java.lang.StringIndexOutOfBoundsException e){
			//System.out.println("out of bounds: " + file.getName()+ " + " + file.getAbsolutePath());

			text = "null";
		}
		
		return text;
	}
	public static boolean moveFile(File source, File destination){
		boolean moved = source.renameTo(new File(destination, source.getName()));
		// Move file to new directory
		if(!moved)
			source.delete();
				
		return moved;
		
	}
	public static boolean moveDoc(File source, File destination){
		int count = 0;
		boolean sucess = false;
		while(!source.renameTo(new File(destination, source.getName())) || sucess){
			sucess = source.renameTo(new File(destination, "" + count++ + source.getName()));
			System.out.println(source + "    " + count + "  " + sucess);
		}
		
				
		return true;
		
	}
	public static void cleanUp(File root){
		if(root.isDirectory())
			for(File f : root.listFiles())
				if(f.isDirectory())
					if(f.list().length ==0)
						f.delete();
					else{
						cleanUp(f);
						if(f.list().length ==0)
							f.delete();
					}
	}

}
