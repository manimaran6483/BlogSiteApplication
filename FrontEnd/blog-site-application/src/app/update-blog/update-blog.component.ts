import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BlogService } from 'app/services/blog.service';
import { Blog } from '../Blog';

@Component({
  selector: 'app-update-blog',
  templateUrl: './update-blog.component.html',
  styleUrls: ['./update-blog.component.css']
})
export class UpdateBlogComponent implements OnInit {
  blog!: Blog;
  constructor(private blogService: BlogService,
    private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    let id = this.route.snapshot.params['id'];
    console.log(id);
    // this.blogService.getEmployeeById(id).subscribe((data) => {
    //   this.blog = data;
    // });
  }
  onSubmit(blog: Blog) {
    this.blogService.updateBlog(blog).subscribe(data => {
      this.router.navigate(["/blogs"]);
    })
  }
  onCancel() {
    this.router.navigate(["/blogs"]);
  }


}
