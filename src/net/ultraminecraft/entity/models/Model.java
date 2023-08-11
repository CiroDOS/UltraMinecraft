package net.ultraminecraft.entity.models;

import org.sibermatica.lang.Renderizable;

public abstract class Model implements Renderizable {

	protected float height;
	protected float width;
	
	protected Model(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
}
