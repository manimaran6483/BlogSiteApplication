import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BlogService } from 'app/services/blog.service';
import { Blog } from '../Blog';

@Component({
  selector: 'app-delete-blog',
  templateUrl: './delete-blog.component.html',
  styleUrls: ['./delete-blog.component.css']
})
export class DeleteBlogComponent implements OnInit {
  blog!: Blog;
  constructor(private blogService: BlogService,
    private router: Router,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    let id = this.route.snapshot.params['id'];
    // this.blogService.getEmployeeById(id).subscribe(data => {
    //   this.blog = data;
    // });
  }
  onDelete() {
    this.blogService.deleteBlog(this.blog.id).subscribe(data => {
      this.router.navigate(["/blogs"]);
    });

  }
  onCancel() {
    this.router.navigate(["/blogs"]);
  }

}
