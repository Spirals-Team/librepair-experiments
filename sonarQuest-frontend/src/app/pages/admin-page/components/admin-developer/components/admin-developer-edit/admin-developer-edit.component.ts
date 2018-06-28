import {MAT_DIALOG_DATA} from '@angular/material';
import {MatDialogRef} from '@angular/material';
import {AdminDeveloperComponent} from './../../admin-developer.component';
import {Component, OnInit, Inject} from '@angular/core';
import {UserService} from '../../../../../../services/user.service';
import {User} from '../../../../../../Interfaces/User';
import {ImageService} from '../../../../../../services/image.service';
import {WorldService} from '../../../../../../services/world.service';
import {UserToWorld} from '../../../../../../Interfaces/UserToWorld';
import {ITdDataTableColumn} from '@covalent/core';
import {UserToWorldService} from '../../../../../../services/user-to-world.service';

@Component({
  selector: 'app-admin-developer-edit',
  templateUrl: './admin-developer-edit.component.html',
  styleUrls: ['./admin-developer-edit.component.css']
})
export class AdminDeveloperEditComponent implements OnInit {

  imageToShow: any;
  userToWorlds: UserToWorld[];

  columns: ITdDataTableColumn[] = [
    {name: 'userId', label: 'UserId', hidden: true},
    {name: 'worldId', label: 'WorldId', hidden: true},
    {name: 'worldName', label: 'World'},
    {name: 'editJoined', label: 'Joined'},
  ];

  constructor(
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public user: User,
    private imageService: ImageService,
    private userToWorldService: UserToWorldService
  ) {
  }

  ngOnInit() {
    this.loadImages();
    this.userToWorldService.getUserToWorlds(this.user).then(userToWorlds => this.userToWorlds = userToWorlds);
  }

  editDeveloper() {
    this.userToWorldService.saveUserToWorlds(this.userToWorlds);
    this.userService.updateUser(this.user).then(() => {
      this.dialogRef.close(true);
    })
  }

  cancel() {
    this.dialogRef.close(false);
  }

  loadImages() {
    this.userService.getImageForUser(this.user).subscribe((blob) => {
      this.imageService.createImageFromBlob(blob).subscribe(image => this.imageToShow = image);
    });
  }

}
