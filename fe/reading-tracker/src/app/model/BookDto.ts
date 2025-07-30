import { AuthorDto } from './AuthorDto';

export interface BookDto {
  id: number;
  title: string;
  authorDto: AuthorDto;
  pages: number;
  readPages: number;
  description: string;
  read: boolean;
}