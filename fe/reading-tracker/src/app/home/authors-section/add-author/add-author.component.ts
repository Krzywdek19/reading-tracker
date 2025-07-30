import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AuthorService } from '../author.service';
import { Validators } from '@angular/forms';
import { CreateAuthorDto } from './model/CreateAuthorDto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-author',
  imports: [ReactiveFormsModule],
  templateUrl: './add-author.component.html',
  styleUrl: './add-author.component.scss'
})
export class AddAuthorComponent {
  authorForm: FormGroup;

  constructor(private fb: FormBuilder, private authorService: AuthorService, private router: Router) {
    this.authorForm = this.fb.group({
      name: ['', Validators.required],
      lastName: ['', Validators.required],
      birthDate: ['', Validators.required],
      description: ['']
    });
  }

  onSubmit(): void {
    if (this.authorForm.valid) {
      const dto: CreateAuthorDto = this.authorForm.value;

      dto.birthDate = this.formatDate(dto.birthDate);

      this.authorService.createAuthor(dto).subscribe({
        next: () => {
          console.log('Added author!');
          this.router.navigate(['/authors'])
        },
        error: (err) => console.error('Error ocours', err)
      });
    }
  }

  formatDate(date: any): string {
    return new Date(date).toISOString().split('T')[0];
  }
}