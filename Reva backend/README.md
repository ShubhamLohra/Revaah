# Wedding Event Customization Frontend

A beautiful, modern React frontend for the Wedding Event Customization system. Built with React, TypeScript, Tailwind CSS, and Framer Motion for smooth animations.

## 🚀 Features

- **Modern Design**: Beautiful, responsive UI inspired by top wedding planning sites
- **Event Selection**: Choose from various wedding events (Haldi, Mehendi, Sangeet, etc.)
- **Decoration Types**: Select specific decoration areas for each event
- **Image Gallery**: Browse and select images with smooth animations
- **PDF Generation**: Generate beautiful PDFs with selected images
- **Responsive**: Mobile-first design that works on all devices
- **Smooth Animations**: Framer Motion for delightful user interactions
- **TypeScript**: Full type safety throughout the application

## 🛠️ Tech Stack

- **React 19** - Latest React with modern features
- **TypeScript** - Type safety and better developer experience
- **Tailwind CSS** - Utility-first CSS framework
- **Framer Motion** - Animation library for smooth transitions
- **React Router** - Client-side routing
- **Axios** - HTTP client for API calls
- **Lucide React** - Beautiful icon library
- **Vite** - Fast build tool and development server

## 📦 Installation

1. **Clone the repository** (if not already done):
   ```bash
   cd wedding-event-frontend
   ```

2. **Install dependencies**:
   ```bash
   npm install
   ```

3. **Start the development server**:
   ```bash
   npm run dev
   ```

4. **Open your browser** and navigate to `http://localhost:5173`

## 🎨 Design Features

### Color Palette
- **Primary**: Rose gradient (rose-500 to rose-600)
- **Secondary**: Gold gradient (gold-400 to gold-500)
- **Accent**: Purple and green gradients for features
- **Neutral**: Gray scale for text and backgrounds

### Typography
- **Headings**: Playfair Display (serif) for elegant wedding feel
- **Body**: Inter (sans-serif) for readability

### Animations
- **Page Transitions**: Smooth fade-in and slide-up effects
- **Hover Effects**: Scale, rotate, and shadow animations
- **Loading States**: Spinner animations with smooth transitions
- **Image Gallery**: Smooth image selection and modal animations

## 📱 Pages

### 1. Home Page (`/`)
- Hero section with call-to-action
- Feature overview with animated icons
- Beautiful gradient backgrounds
- Responsive design

### 2. Event Selection (`/events`)
- Grid of available events
- Search functionality
- Event cards with hover effects
- Loading and error states

### 3. Decoration Types (`/events/:eventId/decorations`)
- Decoration types for selected event
- Search and filter options
- Progress indicator
- Navigation breadcrumbs

### 4. Image Gallery (`/decorations/:decorationTypeId/images`)
- Image grid with selection functionality
- Modal for full-size image viewing
- PDF generation with selected images
- Selected images counter

## 🔧 Configuration

### API Configuration
The frontend connects to the backend API at `http://localhost:8080/api/v1`. To change this:

1. Open `src/services/api.ts`
2. Update the `API_BASE_URL` constant:
   ```typescript
   const API_BASE_URL = 'http://your-backend-url/api/v1';
   ```

### Tailwind Configuration
Custom colors and animations are defined in `tailwind.config.js`:

```javascript
colors: {
  primary: { /* Rose color palette */ },
  gold: { /* Gold color palette */ },
  rose: { /* Rose color palette */ }
}
```

## 🚀 Deployment

### Build for Production
```bash
npm run build
```

### Preview Production Build
```bash
npm run preview
```

### Deploy to Vercel
1. Install Vercel CLI: `npm i -g vercel`
2. Run: `vercel`
3. Follow the prompts

### Deploy to Netlify
1. Build the project: `npm run build`
2. Upload the `dist` folder to Netlify
3. Configure redirects for SPA routing

## 🎯 Usage

1. **Start the Backend**: Make sure the Spring Boot backend is running on port 8080
2. **Start the Frontend**: Run `npm run dev` in the frontend directory
3. **Open Browser**: Navigate to `http://localhost:5173`
4. **Select Event**: Choose from available wedding events
5. **Choose Decorations**: Select decoration types for the event
6. **Pick Images**: Browse and select your favorite images
7. **Generate PDF**: Download a beautiful PDF with your selections

## 🔍 Development

### Project Structure
```
src/
├── components/          # Reusable UI components
│   ├── Header.tsx      # Navigation header
│   └── Footer.tsx      # Site footer
├── context/            # React context providers
│   └── SelectedImagesContext.tsx
├── pages/              # Page components
│   ├── HomePage.tsx
│   ├── EventSelectionPage.tsx
│   ├── DecorationTypesPage.tsx
│   └── ImageGalleryPage.tsx
├── services/           # API services
│   └── api.ts
├── App.tsx             # Main app component
├── main.tsx            # App entry point
└── index.css           # Global styles
```

### Available Scripts
- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint

## 🎨 Customization

### Adding New Colors
1. Update `tailwind.config.js`
2. Add new color palette
3. Use in components with Tailwind classes

### Adding New Animations
1. Define keyframes in `tailwind.config.js`
2. Add animation classes in `index.css`
3. Use with Framer Motion for complex animations

### Adding New Pages
1. Create component in `src/pages/`
2. Add route in `App.tsx`
3. Update navigation in `Header.tsx`

## 🐛 Troubleshooting

### Common Issues

1. **API Connection Error**
   - Ensure backend is running on port 8080
   - Check CORS configuration in backend
   - Verify API_BASE_URL in api.ts

2. **Images Not Loading**
   - Check image URLs in backend response
   - Verify CORS headers for image domains
   - Check browser console for errors

3. **PDF Generation Fails**
   - Ensure backend PDF service is working
   - Check selected images array is not empty
   - Verify PDF endpoint is accessible

### Debug Mode
Enable debug logging by opening browser console. All API calls and errors are logged.

## 📄 License

This project is part of the Wedding Event Customization system. All rights reserved.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

---

**Made with ❤️ for your special day**