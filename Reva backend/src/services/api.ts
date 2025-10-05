import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor
api.interceptors.request.use(
  (config) => {
    console.log(`Making ${config.method?.toUpperCase()} request to ${config.url}`);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    console.error('API Error:', error.response?.data || error.message);
    return Promise.reject(error);
  }
);

export interface Event {
  id: number;
  name: string;
  description: string;
  isActive: boolean;
  createdAt: string;
  updatedAt: string;
  decorationTypes: DecorationType[];
}

export interface DecorationType {
  id: number;
  name: string;
  description: string;
  eventId: number;
  eventName: string;
  isActive: boolean;
  createdAt: string;
  updatedAt: string;
  decorationImages: DecorationImage[];
}

export interface DecorationImage {
  id: number;
  name: string;
  imageUrl: string;
  description: string;
  decorationTypeId: number;
  decorationTypeName: string;
  isActive: boolean;
  displayOrder: number;
  createdAt: string;
  updatedAt: string;
}

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
  errorCode?: string;
}

export interface PdfGenerationRequest {
  eventId: number;
  eventName: string;
  selectedImages: {
    imageId: number;
    imageName: string;
    imageUrl: string;
    decorationTypeName: string;
  }[];
}

// Event API
export const eventApi = {
  getAllEvents: (): Promise<ApiResponse<Event[]>> =>
    api.get('/events').then(res => res.data),
  
  getEventById: (id: number): Promise<ApiResponse<Event>> =>
    api.get(`/events/${id}`).then(res => res.data),
  
  searchEvents: (query: string): Promise<ApiResponse<Event[]>> =>
    api.get(`/events/search?q=${encodeURIComponent(query)}`).then(res => res.data),
};

// Decoration Type API
export const decorationTypeApi = {
  getAllDecorationTypes: (): Promise<ApiResponse<DecorationType[]>> =>
    api.get('/decoration-types').then(res => res.data),
  
  getDecorationTypesByEventId: (eventId: number): Promise<ApiResponse<DecorationType[]>> =>
    api.get(`/decoration-types/event/${eventId}`).then(res => res.data),
  
  getDecorationTypeById: (id: number): Promise<ApiResponse<DecorationType>> =>
    api.get(`/decoration-types/${id}`).then(res => res.data),
};

// Decoration Image API
export const decorationImageApi = {
  getAllDecorationImages: (): Promise<ApiResponse<DecorationImage[]>> =>
    api.get('/decoration-images').then(res => res.data),
  
  getDecorationImagesByDecorationTypeId: (decorationTypeId: number): Promise<ApiResponse<DecorationImage[]>> =>
    api.get(`/decoration-images/decoration-type/${decorationTypeId}`).then(res => res.data),
  
  getDecorationImageById: (id: number): Promise<ApiResponse<DecorationImage>> =>
    api.get(`/decoration-images/${id}`).then(res => res.data),
};

// PDF Generation API
export const pdfApi = {
  generatePdf: (request: PdfGenerationRequest): Promise<Blob> =>
    api.post('/pdf/generate', request, { responseType: 'blob' }).then(res => res.data),
  
  generatePdfBase64: (request: PdfGenerationRequest): Promise<ApiResponse<string>> =>
    api.post('/pdf/generate-base64', request).then(res => res.data),
};

export default api;