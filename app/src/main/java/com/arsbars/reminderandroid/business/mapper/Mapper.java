package com.arsbars.reminderandroid.business.mapper;

import com.arsbars.reminderandroid.business.dto.GalleryItemDto;
import com.arsbars.reminderandroid.data.galleryItem.GalleryItemModel;
import com.arsbars.reminderandroid.viewmodels.GalleryItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static GalleryItemDto ToDto(GalleryItemViewModel viewModel) {
        return new GalleryItemDto(viewModel.getId(),
                viewModel.getImagePath(),
                viewModel.getThumbnail(),
                viewModel.isVideo(),
                viewModel.getVideoPath(),
                viewModel.isLandscape(),
                viewModel.getNoteId());
    }

    public static GalleryItemDto ToDto(GalleryItemModel model) {
        boolean isVideo = model.isVideo() == 1;
        boolean isLandscape = model.getLandscape() == 1;

        return new GalleryItemDto(model.getId(),
                model.getImagePath(),
                model.getThumbnail(),
                isVideo,
                model.getVideoPath(),
                isLandscape,
                model.getNoteId());
    }

    public static List<GalleryItemViewModel> ToViewModels(List<GalleryItemDto> dtoList) {
        List<GalleryItemViewModel> result = new ArrayList<>();
        for (GalleryItemDto dto : dtoList) {
            result.add(Mapper.ToViewModel(dto));
        }
        return result;
    }

    public static GalleryItemViewModel ToViewModel(GalleryItemDto dto) {
        return new GalleryItemViewModel(dto.getId(),
                dto.getImagePath(),
                dto.getThumbnail(),
                dto.isVideo(),
                dto.getVideoPath(),
                dto.isLandscape(),
                dto.getNoteId());
    }
}
