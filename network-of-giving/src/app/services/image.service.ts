import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  
  

  constructor(private httpClient: HttpClient) { }

  public uploadImage(imageData,charityId){
    
    return  this.httpClient.post('http://localhost:8080/images/upload/'+charityId, imageData);
  }

  public getImage(id)
  {
    return this.httpClient.get('http://localhost:8080/images/get/' + id);
    // .subscribe(
    //   res => {
    //     this.retrieveResonse = res;
    //     this.base64Data = this.retrieveResonse.picByte;
    //     this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
    //   }
    // );
  }
}
