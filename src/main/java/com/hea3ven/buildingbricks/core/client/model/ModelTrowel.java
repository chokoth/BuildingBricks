package com.hea3ven.buildingbricks.core.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.vecmath.Vector3f;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.client.model.ISmartItemModel;

import com.hea3ven.buildingbricks.ModBuildingBricks;
import com.hea3ven.buildingbricks.core.materials.Material;

@SuppressWarnings("deprecation")
public class ModelTrowel implements ISmartItemModel {
	public static HashMap<Material, ModelTrowel> models = new HashMap<Material, ModelTrowel>();

	private TextureAtlasSprite texture;
	private ItemCameraTransforms cameraTransforms;
	private HashMap<EnumFacing, List> faces = new HashMap<EnumFacing, List>();
	private List quads;

	public ModelTrowel(IBakedModel baseModel) {
		this(baseModel, null);
	}

	public ModelTrowel(IBakedModel baseModel, IBakedModel matModel) {
		cameraTransforms = new ItemCameraTransforms(
				new ItemTransformVec3f(new Vector3f(0, 90, -130), new Vector3f(0, 0.15f, -0.15f),
						new Vector3f(1, 1, 1)),
				new ItemTransformVec3f(new Vector3f(-10, 240, 10), new Vector3f(0.1f, 0.1f, 0),
						new Vector3f(1.3f, 1.3f, 1.3f)),
				new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1, 1, 1)),
				new ItemTransformVec3f(new Vector3f(), new Vector3f(), new Vector3f(1, 1, 1)));
		texture = baseModel.getTexture();
		for (EnumFacing side : EnumFacing.VALUES) {
			List sideFaces = new ArrayList(baseModel.getFaceQuads(side));
			if (matModel != null) {
				sideFaces.addAll(matModel.getFaceQuads(side));
			}
			faces.put(side, sideFaces);
		}
		quads = new ArrayList(baseModel.getGeneralQuads());
		if (matModel != null)
			quads.addAll(matModel.getGeneralQuads());
	}

	@Override
	public List getFaceQuads(EnumFacing side) {
		return faces.get(side);
	}

	@Override
	public List getGeneralQuads() {
		return quads;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getTexture() {
		return texture;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return cameraTransforms;
	}

	@Override
	public IBakedModel handleItemState(ItemStack stack) {
		Material mat = ModBuildingBricks.trowel.getBindedMaterial(stack);
		if (mat == null)
			return this;
		else {
			return models.get(mat);
		}
	}

}