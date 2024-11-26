import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-document-scanner' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const DocumentScanner = NativeModules.DocumentScanner
  ? NativeModules.DocumentScanner
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );
  
    

export function startDocumentScanner(): Promise<Object> {
  return DocumentScanner.startDocumentScanner();
}
export function openGalleryScanner(): Promise<Object> {
  return DocumentScanner.openGalleryScanner();
}

