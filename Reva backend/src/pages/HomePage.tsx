import React from 'react';
import { Link } from 'react-router-dom';
import { motion } from 'framer-motion';
import { ArrowRight, Heart, Sparkles, Camera, Palette, Download } from 'lucide-react';

const HomePage: React.FC = () => {
  const features = [
    {
      icon: <Heart className="h-8 w-8" />,
      title: 'Event Selection',
      description: 'Choose from a variety of wedding events like Haldi, Mehendi, Sangeet, and more.',
      color: 'from-rose-500 to-rose-600'
    },
    {
      icon: <Palette className="h-8 w-8" />,
      title: 'Decoration Types',
      description: 'Select specific decoration areas like Mandap, Entrance, Food Stage, and more.',
      color: 'from-gold-500 to-gold-600'
    },
    {
      icon: <Camera className="h-8 w-8" />,
      title: 'Image Gallery',
      description: 'Browse through beautiful images and select your favorites for each decoration.',
      color: 'from-purple-500 to-purple-600'
    },
    {
      icon: <Download className="h-8 w-8" />,
      title: 'PDF Generation',
      description: 'Generate a beautiful PDF with all your selected images for easy sharing.',
      color: 'from-green-500 to-green-600'
    }
  ];

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

  return (
    <div className="min-h-screen">
      {/* Hero Section */}
      <section className="relative overflow-hidden bg-gradient-to-br from-rose-50 via-white to-gold-50 py-20">
        <div className="absolute inset-0 bg-[url('data:image/svg+xml,%3Csvg width="60" height="60" viewBox="0 0 60 60" xmlns="http://www.w3.org/2000/svg"%3E%3Cg fill="none" fill-rule="evenodd"%3E%3Cg fill="%23fecaca" fill-opacity="0.1"%3E%3Ccircle cx="30" cy="30" r="2"/%3E%3C/g%3E%3C/g%3E%3C/svg%3E')] opacity-20"></div>
        
        <div className="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
            className="text-center"
          >
            <motion.div
              initial={{ scale: 0 }}
              animate={{ scale: 1 }}
              transition={{ duration: 0.5, delay: 0.2 }}
              className="inline-flex items-center space-x-2 bg-rose-100 text-rose-700 px-4 py-2 rounded-full text-sm font-medium mb-8"
            >
              <Sparkles className="h-4 w-4" />
              <span>Your Dream Wedding Awaits</span>
            </motion.div>
            
            <h1 className="text-5xl md:text-7xl font-serif font-bold mb-6">
              <span className="gradient-text">Create Your</span>
              <br />
              <span className="text-gray-900">Perfect Wedding</span>
            </h1>
            
            <p className="text-xl text-gray-600 mb-8 max-w-3xl mx-auto leading-relaxed">
              Design every detail of your special day with our comprehensive event customization platform. 
              From event selection to decoration planning, we make your wedding dreams come true.
            </p>
            
            <motion.div
              initial={{ opacity: 0, y: 20 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ duration: 0.5, delay: 0.4 }}
              className="flex flex-col sm:flex-row gap-4 justify-center"
            >
              <Link
                to="/events"
                className="btn-primary inline-flex items-center space-x-2 text-lg px-8 py-4"
              >
                <span>Start Planning</span>
                <ArrowRight className="h-5 w-5" />
              </Link>
              
              <button className="btn-outline inline-flex items-center space-x-2 text-lg px-8 py-4">
                <span>Learn More</span>
              </button>
            </motion.div>
          </motion.div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-20 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <motion.div
            initial={{ opacity: 0, y: 30 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
            className="text-center mb-16"
          >
            <h2 className="text-4xl md:text-5xl font-serif font-bold mb-6">
              <span className="gradient-text">How It Works</span>
            </h2>
            <p className="text-xl text-gray-600 max-w-3xl mx-auto">
              Our simple 4-step process makes wedding planning effortless and enjoyable
            </p>
          </motion.div>

          <motion.div
            variants={containerVariants}
            initial="hidden"
            whileInView="visible"
            viewport={{ once: true }}
            className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8"
          >
            {features.map((feature, index) => (
              <motion.div
                key={index}
                variants={itemVariants}
                className="text-center group"
              >
                <motion.div
                  whileHover={{ scale: 1.1, rotate: 5 }}
                  transition={{ type: "spring", stiffness: 300 }}
                  className={`inline-flex items-center justify-center w-16 h-16 rounded-full bg-gradient-to-r ${feature.color} text-white mb-6 group-hover:shadow-lg transition-shadow duration-300`}
                >
                  {feature.icon}
                </motion.div>
                <h3 className="text-xl font-semibold mb-4 text-gray-900">
                  {feature.title}
                </h3>
                <p className="text-gray-600 leading-relaxed">
                  {feature.description}
                </p>
              </motion.div>
            ))}
          </motion.div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-20 bg-gradient-to-r from-rose-500 to-gold-500">
        <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <motion.div
            initial={{ opacity: 0, y: 30 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
          >
            <h2 className="text-4xl md:text-5xl font-serif font-bold text-white mb-6">
              Ready to Plan Your Dream Wedding?
            </h2>
            <p className="text-xl text-rose-100 mb-8">
              Join thousands of couples who have created their perfect wedding with our platform
            </p>
            <Link
              to="/events"
              className="inline-flex items-center space-x-2 bg-white text-rose-600 hover:bg-rose-50 font-semibold py-4 px-8 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 text-lg"
            >
              <span>Get Started Now</span>
              <ArrowRight className="h-5 w-5" />
            </Link>
          </motion.div>
        </div>
      </section>
    </div>
  );
};

export default HomePage;