import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { motion } from 'framer-motion';
import Header from './components/Header';
import Footer from './components/Footer';
import HomePage from './pages/HomePage';
import EventSelectionPage from './pages/EventSelectionPage';
import DecorationTypesPage from './pages/DecorationTypesPage';
import ImageGalleryPage from './pages/ImageGalleryPage';
import { SelectedImagesProvider } from './context/SelectedImagesContext';

const App: React.FC = () => {
  return (
    <SelectedImagesProvider>
      <Router>
        <div className="min-h-screen bg-gradient-to-br from-rose-50 via-white to-gold-50">
          <Header />
          <motion.main
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.5 }}
            className="min-h-screen"
          >
            <Routes>
              <Route path="/" element={<HomePage />} />
              <Route path="/events" element={<EventSelectionPage />} />
              <Route path="/events/:eventId/decorations" element={<DecorationTypesPage />} />
              <Route path="/decorations/:decorationTypeId/images" element={<ImageGalleryPage />} />
            </Routes>
          </motion.main>
          <Footer />
        </div>
      </Router>
    </SelectedImagesProvider>
  );
};

export default App;