package com.codeforanyone.edujavajsp.model;

/**
 * Indicates one lolcat image with its metadata such as title, image format, and
 * id. This class can be referred to as a database entity, a model object, a
 * business domain object, or a bean (all are true and mean essentially the same
 * thing in this case).
 * 
 * @author jennybrown
 *
 */
public class LOLCat {

	Integer id;
	String title;
	String filename;
	String imageFormat;
	byte[] imageData;
	/** Timestamp of first upload */
	java.util.Date created;
	
	public String toString()
	{
		return "LOLCat " + id + ":" + title + ":" + filename + ":" + imageFormat + ":" + created + ". " 
				+ (imageData == null ? "0" : "" + imageData.length) + " bytes";
	}

	public LOLCat() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getImageFormat() {
		return imageFormat;
	}

	public void setImageFormat(String imageFormat) {
		this.imageFormat = imageFormat;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public java.util.Date getCreated() {
		return created;
	}

	public void setCreated(java.util.Date created) {
		this.created = created;
	}

}
