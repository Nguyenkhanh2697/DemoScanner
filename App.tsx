import { NativeModules } from 'react-native';
import React from 'react';
import { Button, Alert, View } from 'react-native';

const { DocumentScanner } = NativeModules;

const App = () => {
  const handleStartScanner = async () => {
    try {
      console.log('Starting Document Scanner...');
      const result = await DocumentScanner.startDocumentScanner();
      console.log('Scan Result:', result);
      Alert.alert('Scan Successful', JSON.stringify(result));
    } catch (error:any) {
      console.error('Scan Error:', error);
      Alert.alert('Scan Failed', error.message || 'Unknown error');
    }
  };

  const handleOpenGallery = async () => {
    try {
      console.log('Opening Gallery Scanner...');
      const result = await DocumentScanner.openGalleryScanner();
      console.log('Gallery Scan Result:', result);
      Alert.alert('Gallery Opened', JSON.stringify(result));
    } catch (error:any) {
      console.error('Gallery Open Error:', error);
      Alert.alert('Gallery Open Failed', error.message || 'Unknown error');
    }
  };

  return (
    <View style={{ padding: 20 }}>
      <Button color={'red'} title="Start Document Scanner" onPress={handleStartScanner} />
      <Button title="Open Gallery Scanner" onPress={handleOpenGallery} />
    </View>
  );
};

export default App;
