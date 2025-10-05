import React from 'react';
import { motion } from 'framer-motion';
import { Heart, Mail, Phone, MapPin, Instagram, Facebook, Twitter } from 'lucide-react';

const Footer: React.FC = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="bg-gradient-to-r from-rose-900 via-rose-800 to-gold-600 text-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          {/* Company Info */}
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
            className="col-span-1 md:col-span-2"
          >
            <div className="flex items-center space-x-2 mb-4">
              <Heart className="h-8 w-8 text-rose-200" fill="currentColor" />
              <span className="text-2xl font-serif font-bold">Wedding Dreams</span>
            </div>
            <p className="text-rose-100 mb-4 max-w-md">
              Creating magical moments that last a lifetime. Let us help you design the perfect wedding 
              with our comprehensive event customization platform.
            </p>
            <div className="flex space-x-4">
              <a href="#" className="text-rose-200 hover:text-white transition-colors duration-300">
                <Instagram className="h-6 w-6" />
              </a>
              <a href="#" className="text-rose-200 hover:text-white transition-colors duration-300">
                <Facebook className="h-6 w-6" />
              </a>
              <a href="#" className="text-rose-200 hover:text-white transition-colors duration-300">
                <Twitter className="h-6 w-6" />
              </a>
            </div>
          </motion.div>

          {/* Quick Links */}
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5, delay: 0.1 }}
          >
            <h3 className="text-lg font-semibold mb-4">Quick Links</h3>
            <ul className="space-y-2">
              <li><a href="/" className="text-rose-100 hover:text-white transition-colors duration-300">Home</a></li>
              <li><a href="/events" className="text-rose-100 hover:text-white transition-colors duration-300">Events</a></li>
              <li><a href="#" className="text-rose-100 hover:text-white transition-colors duration-300">About Us</a></li>
              <li><a href="#" className="text-rose-100 hover:text-white transition-colors duration-300">Contact</a></li>
            </ul>
          </motion.div>

          {/* Contact Info */}
          <motion.div
            initial={{ opacity: 0, y: 20 }}
            whileInView={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5, delay: 0.2 }}
          >
            <h3 className="text-lg font-semibold mb-4">Contact Us</h3>
            <div className="space-y-3">
              <div className="flex items-center space-x-3">
                <Mail className="h-5 w-5 text-rose-200" />
                <span className="text-rose-100">info@weddingdreams.com</span>
              </div>
              <div className="flex items-center space-x-3">
                <Phone className="h-5 w-5 text-rose-200" />
                <span className="text-rose-100">+1 (555) 123-4567</span>
              </div>
              <div className="flex items-center space-x-3">
                <MapPin className="h-5 w-5 text-rose-200" />
                <span className="text-rose-100">New York, NY</span>
              </div>
            </div>
          </motion.div>
        </div>

        <motion.div
          initial={{ opacity: 0 }}
          whileInView={{ opacity: 1 }}
          transition={{ duration: 0.5, delay: 0.3 }}
          className="border-t border-rose-700 mt-8 pt-8 text-center"
        >
          <p className="text-rose-200">
            © {currentYear} Wedding Dreams. All rights reserved. Made with{' '}
            <Heart className="inline h-4 w-4 text-rose-300" fill="currentColor" />{' '}
            for your special day.
          </p>
        </motion.div>
      </div>
    </footer>
  );
};

export default Footer;