package com.example.birdnote.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Bird implements Parcelable{
	private long id;
	private String name;
	private String habitat;
	private String diet;
	private String breeding;
	private String wintering_habits;
	private String where_to_see;
	private String conservation;
	private int primary_colour;
	private int secondary_colour;
	private int crown_colour;
	private int bill_length;
	private int bill_colour;
	private int tail_shape;
	private String latin_name;
	private String description;
	private String created_at;
	private String upated_at;
	private String image;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHabitat() {
		return habitat;
	}

	public void setHabitat(String habitat) {
		this.habitat = habitat;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}
	
	public String getBreeding() {
		return breeding;
	}

	public void setBreeding(String breeding) {
		this.breeding = breeding;
	}

	public String getWinteringHabits() {
		return wintering_habits;
	}

	public void setWinteringHabits(String wintering_habits) {
		this.wintering_habits = wintering_habits;
	}

	public String getWhereToSee() {
		return where_to_see;
	}

	public void setWhereToSee(String where_to_see) {
		this.where_to_see = where_to_see;
	}

	public String getConservation() {
		return conservation;
	}

	public void setConservation(String conservation) {
		this.conservation = conservation;
	}

	public int getPrimaryColour() {
		return primary_colour;
	}

	public void setPrimaryColour(int primary_colour) {
		this.primary_colour = primary_colour;
	}

	public int getSecondaryColour() {
		return secondary_colour;
	}

	public void setSecondaryColour(int secondary_colour) {
		this.secondary_colour = secondary_colour;
	}

	public int getCrownColour() {
		return crown_colour;
	}

	public void setCrownColour(int crown_colour) {
		this.crown_colour = crown_colour;
	}

	public int getBillLength() {
		return bill_length;
	}

	public void setBillLength(int bill_length) {
		this.bill_length = bill_length;
	}

	public int getBillColour() {
		return bill_colour;
	}

	public void setBillColour(int bill_colour) {
		this.bill_colour = bill_colour;
	}

	public int getTailShape() {
		return tail_shape;
	}

	public void setTailShape(int tail_shape) {
		this.tail_shape = tail_shape;
	}
	
	public String getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}
	
	public String getUpdatedAt() {
		return upated_at;
	}

	public void setUpdatedAt(String upated_at) {
		this.upated_at = upated_at;
	}

	public String getLatinName() {
		return latin_name;
	}

	public void setLatinName(String latin_name) {
		this.latin_name = latin_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return name;
	}

    public Bird() {
    }

    public Bird(Parcel in) {
         //Log.i(ReferenceGuide.LOGTAG, "Parcel constructor");
        
         id = in.readLong();
         name = in.readString();
         latin_name = in.readString();
         description = in.readString();
         habitat = in.readString();
         diet = in.readString();
         breeding = in.readString();
         wintering_habits = in.readString();
         where_to_see = in.readString();
         conservation = in.readString();
         image = in.readString();
    }

    @Override
    public int describeContents() {
         return 0;
    }
   
    @Override
    public void writeToParcel(Parcel dest, int flags) {
         //Log.i(ReferenceGuide.LOGTAG, "writeToParcel");
        
         dest.writeLong(id);
         dest.writeString(name);
         dest.writeString(latin_name);
         dest.writeString(description);
         dest.writeString(habitat);
         dest.writeString(diet);
         dest.writeString(breeding);
         dest.writeString(wintering_habits);
         dest.writeString(where_to_see);
         dest.writeString(conservation);
         dest.writeString(image);
    }

    public static final Parcelable.Creator<Bird> CREATOR =
              new Parcelable.Creator<Bird>() {

         @Override
         public Bird createFromParcel(Parcel source) {
              //Log.i(ReferenceGuide.LOGTAG, "createFromParcel");
              return new Bird(source);
         }

         @Override
         public Bird[] newArray(int size) {
              //Log.i(ReferenceGuide.LOGTAG, "newArray");
              return new Bird[size];
         }
    };
}
