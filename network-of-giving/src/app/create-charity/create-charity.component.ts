import { Component, OnInit } from '@angular/core';
import {  CharitiesService } from '../services/charities.service';
import { TokenStorageService } from '../authentication/token-storage.service';
import {Charity} from '../charity-details/charity-details.component'
import { ImageService } from '../services/image.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';

export class CharityToSave
{
   
    owner_username : String;
    name : String;
    description : String;
    required_amount : Number;
    required_participants : Number;
    donated_amount : Number;
    participants : Number;
    completed : Boolean;
    pathToThumbnail : String;

    

    constructor(
      owner_username : String,
      name : String,
      description : String,
      required_amount : Number,
      required_participants : Number,
      donated_amount : Number,
      participants : Number,
      completed : Boolean,
      pathToThumbnail : String)
    {
        
        this.owner_username=owner_username;
        this.name=name;
        this.description=description;
        this.required_amount=required_amount;
        this.required_participants=required_participants;
        this.donated_amount=donated_amount;
        this.participants=participants;
        this.completed=completed;
        this.pathToThumbnail=pathToThumbnail;

        this.completed = this.setCompleted();

    }

    public setCompleted() : boolean
    {
        if(this.required_amount>this.donated_amount) return false;
        if(this.required_participants>this.participants) return false;
        return true;
    }

   public getRemaining() : Number{
      return (this.required_amount.valueOf()-this.donated_amount.valueOf());
    }

    public setOwnerUsername(username : String) : void
    {
      this.owner_username = username;
    }
}


@Component({
  selector: 'app-create-charity',
  templateUrl: './create-charity.component.html',
  styleUrls: ['./create-charity.component.css']
})
export class CreateCharityComponent implements OnInit {

  charity : CharityToSave = new CharityToSave('',null,null,0,0,0,0,false,'');
  
  generatedId : Number;
  errorModal : boolean = false;
  successModal : boolean = null;
  inputOk : boolean = true;
  wrongInputModal : boolean = false;

  previewUrl:any = null;
  fileUploadProgress: string = null;
  uploadedFilePath: string = null;
  selectedFile: File = null;
  imageSrc: string;

  myForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    file: new FormControl('', [Validators.required]),
    fileSource: new FormControl('', [Validators.required])
  });

  constructor(public tokenService : TokenStorageService,
    private charitiesService : CharitiesService,
    private imageService: ImageService) { }

  ngOnInit(): void {
    
    this.charity.setOwnerUsername(this.tokenService.getUsername());
    console.log(this.charity.owner_username);
  }

  onSubmit()
  {
    console.log(this.charity);
    this.checkConstraintsOnFields();
    if(this.inputOk)
    {
      this.charitiesService.addCharity(this.charity).subscribe(
        response => 
        {this.handleSuccessfulCreation(response);
          console.log(response);
        },
        error => this.handleError(error)
      );
    }
    
  }

  checkConstraintsOnFields()
  {
    
    if(this.charity.required_amount == 0 && this.charity.required_participants == 0)
    {
      this.inputOk = false;
      this.wrongInputModal = true;
    }
    if(this.selectedFile===null)
    {
      this.inputOk=false;
      this.wrongInputModal = true;
    }
    if(this.charity.name==='' || this.charity.description==='')
    {
      this.inputOk=false;
      this.wrongInputModal = true;
    }
    if(this.charity.required_amount  <0 || this.charity.required_participants < 0)
    {
      this.inputOk=false;
      this.wrongInputModal = true;
    }
  }

  handleSuccessfulCreation(response){

      this.generatedId = response;
      console.log(this.generatedId);
      this.submitImage();

  }

  handleError(error)
  {
      this.errorModal = true;
      
  }


  get f(){
    return this.myForm.controls;
  }
   
  onFileChange(event) {
    const reader = new FileReader();
    
    if(event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      this.selectedFile = event.target.files[0];
      reader.readAsDataURL(file);
    
      reader.onload = () => {
   
        this.imageSrc = reader.result as string;
     
        this.myForm.patchValue({
          fileSource: reader.result
        });
   
      };
   
    }
  }

  submitImage()
  {
    const uploadImageData = new FormData();
31
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

    console.log(this.myForm.value);

    this.imageService.uploadImage(uploadImageData,this.generatedId)
      .subscribe(res => {
        console.log(res);
        this.successModal = true;
      },
      error => {
        this.handleErrorImageUpload();

      }
      )
  }

  handleErrorImageUpload()
  {
    this.charitiesService.deleteCharity(this.generatedId.toString());
    this.errorModal=true;
  }
  closeErrorModal()
  {
      this.errorModal=false;
      
  }

  closeEWrongInputModal()
  {
    //this.inputOk=true;
    this.wrongInputModal=false;
  }

  emptyName()
  {
    
    if(this.charity.name===null) return true;
    return false;
  }

  emptyDescription()
  {
    if(this.charity.description===null) return true;
    return false;
  }

  checkWrongInputForIcon()
  {
    if(this.charity.required_amount  <0 || this.charity.required_participants < 0)
    {
      this.inputOk=false;
      
    }
  }
  // private onSuccess() {
  //   // this.selectedFile.pending = false;
  //   // this.selectedFile.status = 'ok';
  //   this.successModal = true;
  // }

  // private onError() {
  //   this.selectedFile.pending = false;
  //   this.selectedFile.status = 'fail';
  //   this.selectedFile.src = '';
  // }



  // fileProgress(fileInput : any)
  // {
  //   this.selectedFile = <File>fileInput.target.files[0];
  //   this.preview();
  // }

  // preview()
  // {
  //   var mimeType = this.selectedFile.type;
  //   if (mimeType.match(/image\/*/) == null) {
  //     return;
  //   }
 
  //   var reader = new FileReader();      
  //   reader.readAsDataURL(this.selectedFile); 
  //   reader.onload = (_event) => { 
  //     this.previewUrl = reader.result; 
  //   }
  // }

  // processFile(imageInput: any) {
    
  //     this.imageService.uploadImage(this.selectedFile,this.generatedId).subscribe(
  //       (res) => {
  //         console.log(res);
  //         // this.uploadedFilePath = res.data.filePath;
  //         alert('SUCCESS !!');
  //       },
  //       (err) => {
  //         alert('Error !!');
  //       });
   

    
  // }

  


}
