import React, { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { motion } from 'framer-motion';
import { Heart, Menu, X, Sparkles } from 'lucide-react';
import { useSelectedImages } from '../context/SelectedImagesContext';

const Header: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const location = useLocation();
  const { selectedImages } = useSelectedImages();

  const navigation = [
    { name: 'Home', href: '/' },
    { name: 'Events', href: '/events' },
  ];

  const isActive = (path: string) => location.pathname === path;

  return (
    <motion.header
      initial={{ y: -100 }}
      animate={{ y: 0 }}
      transition={{ duration: 0.5 }}
      className="sticky top-0 z-50 glass-effect border-b border-white/20"
    >
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo */}
          <Link to="/" className="flex items-center space-x-2 group">
            <motion.div
              whileHover={{ scale: 1.1, rotate: 5 }}
              transition={{ type: "spring", stiffness: 300 }}
              className="relative"
            >
              <Heart className="h-8 w-8 text-rose-500" fill="currentColor" />
              <Sparkles className="h-4 w-4 text-gold-400 absolute -top-1 -right-1" />
            </motion.div>
            <span className="text-2xl font-serif font-bold gradient-text">
              Wedding Dreams
            </span>
          </Link>

          {/* Desktop Navigation */}
          <nav className="hidden md:flex space-x-8">
            {navigation.map((item) => (
              <Link
                key={item.name}
                to={item.href}
                className={`relative px-3 py-2 rounded-md text-sm font-medium transition-all duration-300 ${
                  isActive(item.href)
                    ? 'text-rose-600 bg-rose-50'
                    : 'text-gray-700 hover:text-rose-600 hover:bg-rose-50'
                }`}
              >
                {item.name}
                {isActive(item.href) && (
                  <motion.div
                    layoutId="activeTab"
                    className="absolute inset-0 bg-rose-50 rounded-md"
                    initial={false}
                    transition={{ type: "spring", stiffness: 300, damping: 30 }}
                  />
                )}
              </Link>
            ))}
          </nav>

          {/* Selected Images Counter */}
          {selectedImages.length > 0 && (
            <motion.div
              initial={{ scale: 0 }}
              animate={{ scale: 1 }}
              className="hidden md:flex items-center space-x-2 bg-rose-100 text-rose-700 px-3 py-1 rounded-full text-sm font-medium"
            >
              <Heart className="h-4 w-4" fill="currentColor" />
              <span>{selectedImages.length} selected</span>
            </motion.div>
          )}

          {/* Mobile menu button */}
          <div className="md:hidden">
            <button
              onClick={() => setIsMenuOpen(!isMenuOpen)}
              className="text-gray-700 hover:text-rose-600 focus:outline-none focus:text-rose-600"
            >
              {isMenuOpen ? (
                <X className="h-6 w-6" />
              ) : (
                <Menu className="h-6 w-6" />
              )}
            </button>
          </div>
        </div>

        {/* Mobile Navigation */}
        <motion.div
          initial={false}
          animate={{ height: isMenuOpen ? 'auto' : 0 }}
          transition={{ duration: 0.3 }}
          className="md:hidden overflow-hidden"
        >
          <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3">
            {navigation.map((item) => (
              <Link
                key={item.name}
                to={item.href}
                onClick={() => setIsMenuOpen(false)}
                className={`block px-3 py-2 rounded-md text-base font-medium transition-colors duration-300 ${
                  isActive(item.href)
                    ? 'text-rose-600 bg-rose-50'
                    : 'text-gray-700 hover:text-rose-600 hover:bg-rose-50'
                }`}
              >
                {item.name}
              </Link>
            ))}
            {selectedImages.length > 0 && (
              <div className="flex items-center space-x-2 bg-rose-100 text-rose-700 px-3 py-2 rounded-md text-sm font-medium">
                <Heart className="h-4 w-4" fill="currentColor" />
                <span>{selectedImages.length} images selected</span>
              </div>
            )}
          </div>
        </motion.div>
      </div>
    </motion.header>
  );
};

export default Header;