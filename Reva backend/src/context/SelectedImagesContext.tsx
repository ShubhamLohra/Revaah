import React, { createContext, useContext, useState, ReactNode } from 'react';
import { DecorationImage } from '../services/api';

interface SelectedImage {
  imageId: number;
  imageName: string;
  imageUrl: string;
  decorationTypeName: string;
}

interface SelectedImagesContextType {
  selectedImages: SelectedImage[];
  addImage: (image: DecorationImage, decorationTypeName: string) => void;
  removeImage: (imageId: number) => void;
  clearImages: () => void;
  isImageSelected: (imageId: number) => boolean;
  getSelectedImagesByDecorationType: (decorationTypeName: string) => SelectedImage[];
}

const SelectedImagesContext = createContext<SelectedImagesContextType | undefined>(undefined);

export const useSelectedImages = () => {
  const context = useContext(SelectedImagesContext);
  if (!context) {
    throw new Error('useSelectedImages must be used within a SelectedImagesProvider');
  }
  return context;
};

interface SelectedImagesProviderProps {
  children: ReactNode;
}

export const SelectedImagesProvider: React.FC<SelectedImagesProviderProps> = ({ children }) => {
  const [selectedImages, setSelectedImages] = useState<SelectedImage[]>([]);

  const addImage = (image: DecorationImage, decorationTypeName: string) => {
    setSelectedImages(prev => {
      const exists = prev.some(img => img.imageId === image.id);
      if (exists) return prev;
      
      return [...prev, {
        imageId: image.id,
        imageName: image.name,
        imageUrl: image.imageUrl,
        decorationTypeName
      }];
    });
  };

  const removeImage = (imageId: number) => {
    setSelectedImages(prev => prev.filter(img => img.imageId !== imageId));
  };

  const clearImages = () => {
    setSelectedImages([]);
  };

  const isImageSelected = (imageId: number) => {
    return selectedImages.some(img => img.imageId === imageId);
  };

  const getSelectedImagesByDecorationType = (decorationTypeName: string) => {
    return selectedImages.filter(img => img.decorationTypeName === decorationTypeName);
  };

  const value: SelectedImagesContextType = {
    selectedImages,
    addImage,
    removeImage,
    clearImages,
    isImageSelected,
    getSelectedImagesByDecorationType,
  };

  return (
    <SelectedImagesContext.Provider value={value}>
      {children}
    </SelectedImagesContext.Provider>
  );
};