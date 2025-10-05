import React, { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { motion, AnimatePresence } from 'framer-motion';
import { ArrowLeft, Heart, Sparkles, Search, Loader2, Check, Download, Eye, X } from 'lucide-react';
import { decorationImageApi, decorationTypeApi, DecorationImage, DecorationType } from '../services/api';
import { useSelectedImages } from '../context/SelectedImagesContext';
import { pdfApi } from '../services/api';

const ImageGalleryPage: React.FC = () => {
  const { decorationTypeId } = useParams<{ decorationTypeId: string }>();
  const [decorationType, setDecorationType] = useState<DecorationType | null>(null);
  const [images, setImages] = useState<DecorationImage[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedImage, setSelectedImage] = useState<DecorationImage | null>(null);
  const [isGeneratingPdf, setIsGeneratingPdf] = useState(false);

  const { 
    selectedImages, 
    addImage, 
    removeImage, 
    isImageSelected, 
    clearImages 
  } = useSelectedImages();

  useEffect(() => {
    if (decorationTypeId) {
      fetchDecorationTypeAndImages();
    }
  }, [decorationTypeId]);

  const fetchDecorationTypeAndImages = async () => {
    try {
      setLoading(true);
      
      // Fetch decoration type details
      const decorationTypeResponse = await decorationTypeApi.getDecorationTypeById(Number(decorationTypeId));
      if (decorationTypeResponse.success) {
        setDecorationType(decorationTypeResponse.data);
      }

      // Fetch images for this decoration type
      const imagesResponse = await decorationImageApi.getDecorationImagesByDecorationTypeId(Number(decorationTypeId));
      if (imagesResponse.success) {
        setImages(imagesResponse.data);
      } else {
        setError('Failed to fetch images');
      }
    } catch (err) {
      setError('Error loading images. Please try again.');
      console.error('Error fetching images:', err);
    } finally {
      setLoading(false);
    }
  };

  const filteredImages = images.filter(image =>
    image.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
    image.description.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const handleImageSelect = (image: DecorationImage) => {
    if (isImageSelected(image.id)) {
      removeImage(image.id);
    } else {
      addImage(image, decorationType?.name || '');
    }
  };

  const handleGeneratePdf = async () => {
    if (selectedImages.length === 0) {
      alert('Please select at least one image to generate PDF');
      return;
    }

    try {
      setIsGeneratingPdf(true);
      
      const pdfRequest = {
        eventId: decorationType?.eventId || 0,
        eventName: decorationType?.eventName || '',
        selectedImages: selectedImages.map(img => ({
          imageId: img.imageId,
          imageName: img.imageName,
          imageUrl: img.imageUrl,
          decorationTypeName: img.decorationTypeName
        }))
      };

      const response = await pdfApi.generatePdf(pdfRequest);
      
      // Create blob URL and download
      const blob = new Blob([response], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = `wedding-${decorationType?.name?.toLowerCase().replace(/\s+/g, '-')}-images.pdf`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
      
    } catch (err) {
      console.error('Error generating PDF:', err);
      alert('Error generating PDF. Please try again.');
    } finally {
      setIsGeneratingPdf(false);
    }
  };

  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.05
      }
    }
  };

  const itemVariants = {
    hidden: { y: 20, opacity: 0 },
    visible: {
      y: 0,
      opacity: 1,
      transition: {
        duration: 0.5
      }
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="text-center"
        >
          <Loader2 className="h-12 w-12 text-rose-500 animate-spin mx-auto mb-4" />
          <p className="text-gray-600">Loading images...</p>
        </motion.div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="text-center"
        >
          <div className="bg-red-50 border border-red-200 rounded-lg p-6 max-w-md">
            <p className="text-red-600 mb-4">{error}</p>
            <button
              onClick={fetchDecorationTypeAndImages}
              className="btn-primary"
            >
              Try Again
            </button>
          </div>
        </motion.div>
      </div>
    );
  }

  return (
    <div className="min-h-screen py-12">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <motion.div
          initial={{ opacity: 0, y: 30 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.8 }}
          className="text-center mb-12"
        >
          <div className="inline-flex items-center space-x-2 bg-rose-100 text-rose-700 px-4 py-2 rounded-full text-sm font-medium mb-6">
            <Heart className="h-4 w-4" fill="currentColor" />
            <span>Step 3 of 4</span>
          </div>
          
          <h1 className="text-4xl md:text-6xl font-serif font-bold mb-6">
            <span className="gradient-text">{decorationType?.name} Images</span>
          </h1>
          
          <p className="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
            Select your favorite images for {decorationType?.name?.toLowerCase()}. You can choose multiple images.
          </p>

          {/* Search Bar */}
          <div className="relative max-w-md mx-auto mb-8">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400" />
            <input
              type="text"
              placeholder="Search images..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-rose-500 focus:border-transparent transition-all duration-300"
            />
          </div>

          {/* Selected Images Counter and Actions */}
          {selectedImages.length > 0 && (
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              className="flex flex-col sm:flex-row items-center justify-center space-y-4 sm:space-y-0 sm:space-x-4 mb-8"
            >
              <div className="flex items-center space-x-2 bg-rose-100 text-rose-700 px-4 py-2 rounded-full">
                <Heart className="h-4 w-4" fill="currentColor" />
                <span>{selectedImages.length} image{selectedImages.length !== 1 ? 's' : ''} selected</span>
              </div>
              
              <div className="flex space-x-2">
                <button
                  onClick={handleGeneratePdf}
                  disabled={isGeneratingPdf}
                  className="btn-primary inline-flex items-center space-x-2 disabled:opacity-50"
                >
                  {isGeneratingPdf ? (
                    <Loader2 className="h-4 w-4 animate-spin" />
                  ) : (
                    <Download className="h-4 w-4" />
                  )}
                  <span>{isGeneratingPdf ? 'Generating...' : 'Generate PDF'}</span>
                </button>
                
                <button
                  onClick={clearImages}
                  className="btn-outline"
                >
                  Clear All
                </button>
              </div>
            </motion.div>
          )}
        </motion.div>

        {/* Images Grid */}
        <motion.div
          variants={containerVariants}
          initial="hidden"
          animate="visible"
          className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6"
        >
          {filteredImages.map((image, index) => (
            <motion.div
              key={image.id}
              variants={itemVariants}
              whileHover={{ y: -5 }}
              className="group relative"
            >
              <div className="card card-hover overflow-hidden">
                {/* Image */}
                <div className="relative aspect-square overflow-hidden">
                  <img
                    src={image.imageUrl}
                    alt={image.name}
                    className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500"
                    onError={(e) => {
                      const target = e.target as HTMLImageElement;
                      target.src = 'https://via.placeholder.com/400x400/f3f4f6/9ca3af?text=Image+Not+Available';
                    }}
                  />
                  
                  {/* Overlay */}
                  <div className="absolute inset-0 bg-black bg-opacity-0 group-hover:bg-opacity-30 transition-all duration-300 flex items-center justify-center">
                    <button
                      onClick={() => setSelectedImage(image)}
                      className="opacity-0 group-hover:opacity-100 transition-opacity duration-300 bg-white text-gray-900 p-2 rounded-full hover:bg-gray-100"
                    >
                      <Eye className="h-5 w-5" />
                    </button>
                  </div>

                  {/* Selection Indicator */}
                  <button
                    onClick={() => handleImageSelect(image)}
                    className={`absolute top-3 right-3 w-8 h-8 rounded-full flex items-center justify-center transition-all duration-300 ${
                      isImageSelected(image.id)
                        ? 'bg-rose-500 text-white'
                        : 'bg-white bg-opacity-80 text-gray-600 hover:bg-rose-500 hover:text-white'
                    }`}
                  >
                    {isImageSelected(image.id) ? (
                      <Check className="h-5 w-5" />
                    ) : (
                      <Heart className="h-4 w-4" />
                    )}
                  </button>
                </div>

                {/* Image Info */}
                <div className="p-4">
                  <h3 className="font-semibold text-gray-900 mb-2 truncate">
                    {image.name}
                  </h3>
                  <p className="text-sm text-gray-600 line-clamp-2">
                    {image.description}
                  </p>
                </div>
              </div>
            </motion.div>
          ))}
        </motion.div>

        {/* No Results */}
        {filteredImages.length === 0 && searchQuery && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="text-center py-12"
          >
            <div className="bg-gray-50 rounded-lg p-8 max-w-md mx-auto">
              <Search className="h-12 w-12 text-gray-400 mx-auto mb-4" />
              <h3 className="text-lg font-semibold text-gray-900 mb-2">No images found</h3>
              <p className="text-gray-600 mb-4">
                Try searching with different keywords or clear the search to see all images.
              </p>
              <button
                onClick={() => setSearchQuery('')}
                className="btn-outline"
              >
                Clear Search
              </button>
            </div>
          </motion.div>
        )}

        {/* Navigation */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ delay: 0.5 }}
          className="flex justify-between items-center mt-12"
        >
          <Link
            to={`/events/${decorationType?.eventId}/decorations`}
            className="inline-flex items-center space-x-2 text-gray-600 hover:text-rose-600 transition-colors duration-300"
          >
            <ArrowLeft className="h-4 w-4" />
            <span>Back to Decorations</span>
          </Link>

          <div className="text-sm text-gray-500">
            {filteredImages.length} image{filteredImages.length !== 1 ? 's' : ''} available
          </div>
        </motion.div>
      </div>

      {/* Image Modal */}
      <AnimatePresence>
        {selectedImage && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center z-50 p-4"
            onClick={() => setSelectedImage(null)}
          >
            <motion.div
              initial={{ scale: 0.8, opacity: 0 }}
              animate={{ scale: 1, opacity: 1 }}
              exit={{ scale: 0.8, opacity: 0 }}
              className="relative max-w-4xl max-h-full bg-white rounded-lg overflow-hidden"
              onClick={(e) => e.stopPropagation()}
            >
              <button
                onClick={() => setSelectedImage(null)}
                className="absolute top-4 right-4 z-10 bg-white bg-opacity-80 hover:bg-opacity-100 text-gray-600 hover:text-gray-900 p-2 rounded-full transition-all duration-300"
              >
                <X className="h-6 w-6" />
              </button>
              
              <img
                src={selectedImage.imageUrl}
                alt={selectedImage.name}
                className="w-full h-auto max-h-[80vh] object-contain"
                onError={(e) => {
                  const target = e.target as HTMLImageElement;
                  target.src = 'https://via.placeholder.com/800x600/f3f4f6/9ca3af?text=Image+Not+Available';
                }}
              />
              
              <div className="p-6">
                <h3 className="text-2xl font-serif font-bold text-gray-900 mb-2">
                  {selectedImage.name}
                </h3>
                <p className="text-gray-600 mb-4">
                  {selectedImage.description}
                </p>
                <button
                  onClick={() => {
                    handleImageSelect(selectedImage);
                    setSelectedImage(null);
                  }}
                  className={`inline-flex items-center space-x-2 ${
                    isImageSelected(selectedImage.id)
                      ? 'btn-primary'
                      : 'btn-outline'
                  }`}
                >
                  {isImageSelected(selectedImage.id) ? (
                    <>
                      <Check className="h-4 w-4" />
                      <span>Selected</span>
                    </>
                  ) : (
                    <>
                      <Heart className="h-4 w-4" />
                      <span>Select Image</span>
                    </>
                  )}
                </button>
              </div>
            </motion.div>
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );
};

export default ImageGalleryPage;