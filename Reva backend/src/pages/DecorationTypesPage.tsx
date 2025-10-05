import React, { useState, useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { motion } from 'framer-motion';
import { ArrowRight, Heart, Sparkles, Search, Loader2, ArrowLeft, Palette } from 'lucide-react';
import { decorationTypeApi, eventApi, DecorationType, Event } from '../services/api';

const DecorationTypesPage: React.FC = () => {
  const { eventId } = useParams<{ eventId: string }>();
  const [event, setEvent] = useState<Event | null>(null);
  const [decorationTypes, setDecorationTypes] = useState<DecorationType[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [searchQuery, setSearchQuery] = useState('');

  useEffect(() => {
    if (eventId) {
      fetchEventAndDecorationTypes();
    }
  }, [eventId]);

  const fetchEventAndDecorationTypes = async () => {
    try {
      setLoading(true);
      
      // Fetch event details
      const eventResponse = await eventApi.getEventById(Number(eventId));
      if (eventResponse.success) {
        setEvent(eventResponse.data);
      }

      // Fetch decoration types for this event
      const decorationTypesResponse = await decorationTypeApi.getDecorationTypesByEventId(Number(eventId));
      if (decorationTypesResponse.success) {
        setDecorationTypes(decorationTypesResponse.data);
      } else {
        setError('Failed to fetch decoration types');
      }
    } catch (err) {
      setError('Error loading decoration types. Please try again.');
      console.error('Error fetching decoration types:', err);
    } finally {
      setLoading(false);
    }
  };

  const filteredDecorationTypes = decorationTypes.filter(decorationType =>
    decorationType.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
    decorationType.description.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const containerVariants = {
    hidden: { opacity: 0 },
    visible: {
      opacity: 1,
      transition: {
        staggerChildren: 0.1
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
          <p className="text-gray-600">Loading decoration types...</p>
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
              onClick={fetchEventAndDecorationTypes}
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
            <span>Step 2 of 4</span>
          </div>
          
          <h1 className="text-4xl md:text-6xl font-serif font-bold mb-6">
            <span className="gradient-text">{event?.name} Decorations</span>
          </h1>
          
          <p className="text-xl text-gray-600 mb-8 max-w-3xl mx-auto">
            Choose the specific decoration areas you'd like to customize for your {event?.name?.toLowerCase()}.
          </p>

          {/* Search Bar */}
          <div className="relative max-w-md mx-auto">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400" />
            <input
              type="text"
              placeholder="Search decoration types..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-rose-500 focus:border-transparent transition-all duration-300"
            />
          </div>
        </motion.div>

        {/* Decoration Types Grid */}
        <motion.div
          variants={containerVariants}
          initial="hidden"
          animate="visible"
          className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8"
        >
          {filteredDecorationTypes.map((decorationType, index) => (
            <motion.div
              key={decorationType.id}
              variants={itemVariants}
              whileHover={{ y: -5 }}
              className="group"
            >
              <div className="card card-hover h-full">
                <div className="p-8">
                  {/* Decoration Type Icon */}
                  <motion.div
                    whileHover={{ scale: 1.1, rotate: 5 }}
                    transition={{ type: "spring", stiffness: 300 }}
                    className="inline-flex items-center justify-center w-16 h-16 rounded-full bg-gradient-to-r from-gold-500 to-gold-600 text-white mb-6 group-hover:shadow-lg transition-shadow duration-300"
                  >
                    <Palette className="h-8 w-8" />
                  </motion.div>

                  {/* Decoration Type Name */}
                  <h3 className="text-2xl font-serif font-bold text-gray-900 mb-4 group-hover:text-gold-600 transition-colors duration-300">
                    {decorationType.name}
                  </h3>

                  {/* Decoration Type Description */}
                  <p className="text-gray-600 mb-6 leading-relaxed">
                    {decorationType.description}
                  </p>

                  {/* Images Count */}
                  <div className="flex items-center space-x-2 text-sm text-gray-500 mb-6">
                    <Sparkles className="h-4 w-4" />
                    <span>{decorationType.decorationImages?.length || 0} images available</span>
                  </div>

                  {/* Action Button */}
                  <Link
                    to={`/decorations/${decorationType.id}/images`}
                    className="inline-flex items-center space-x-2 w-full justify-center btn-secondary group-hover:shadow-xl transition-all duration-300"
                  >
                    <span>View Images</span>
                    <ArrowRight className="h-5 w-5 group-hover:translate-x-1 transition-transform duration-300" />
                  </Link>
                </div>
              </div>
            </motion.div>
          ))}
        </motion.div>

        {/* No Results */}
        {filteredDecorationTypes.length === 0 && searchQuery && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            className="text-center py-12"
          >
            <div className="bg-gray-50 rounded-lg p-8 max-w-md mx-auto">
              <Search className="h-12 w-12 text-gray-400 mx-auto mb-4" />
              <h3 className="text-lg font-semibold text-gray-900 mb-2">No decoration types found</h3>
              <p className="text-gray-600 mb-4">
                Try searching with different keywords or clear the search to see all decoration types.
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
            to="/events"
            className="inline-flex items-center space-x-2 text-gray-600 hover:text-rose-600 transition-colors duration-300"
          >
            <ArrowLeft className="h-4 w-4" />
            <span>Back to Events</span>
          </Link>

          <div className="text-sm text-gray-500">
            {filteredDecorationTypes.length} decoration type{filteredDecorationTypes.length !== 1 ? 's' : ''} available
          </div>
        </motion.div>
      </div>
    </div>
  );
};

export default DecorationTypesPage;