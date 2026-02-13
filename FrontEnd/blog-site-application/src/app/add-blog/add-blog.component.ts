	import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Blog } from 'app/Blog';
import { BlogService } from 'app/services/blog.service';

@Component({
  selector: 'app-add-blog',
  templateUrl: './add-blog.component.html',
  styleUrls: ['./add-blog.component.css']
})
export class AddBlogComponent {

  constructor(private  blogService: BlogService,
    private router: Router) { }

  onSubmit(blog: Blog) {
    this.blogService.addBlog(blog).subscribe(data => {
      this.router.navigate(["/blogs"]);
    });
  }
  onCancel() {
    this.router.navigate(["/blogs"]);
  }
}
