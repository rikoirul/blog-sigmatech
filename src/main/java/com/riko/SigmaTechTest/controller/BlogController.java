package com.riko.SigmaTechTest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.riko.SigmaTechTest.model.BlogModel;
import com.riko.SigmaTechTest.repo.BlogRepo;

@RestController
public class BlogController {

	@Autowired
	private BlogRepo blogRepo;
	
	@GetMapping ("/getlistBlog")
	public ResponseEntity<Map<String, Object>> getlistBlog() throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			List<BlogModel> blogList = new ArrayList<>();
			blogRepo.findByIsDelete(false).forEach(blogList::add);
			if (!blogList.isEmpty()){
				List<Map<String, Object>> output = new ArrayList<>();
				for (BlogModel blog : blogList){
					Map<String, Object> data = new HashMap<>();
					data.put("author", blog.getAuthor());
					data.put("title", blog.getTitle());
					data.put("body", blog.getBody());
					data.put("id", blog.getId());
					output.add(data);
				}
				response.put("data", output);
				response.put("status", true);
				response.put("message", null);
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			} else {
				response.put("data", blogList);
				response.put("status", true);
				response.put("message", "No Data Found");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.put("data", null);
			response.put("status", false);
			response.put("message", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping ("/getBlogById/{id}")
	public ResponseEntity<Map<String, Object>> getBlogById(@PathVariable Long id) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			Optional<BlogModel> data = blogRepo.findByIdAndIsDelete(id, false);
			
			if (data.isPresent()){
				response.put("data", data);
				response.put("status", true);
				response.put("message", null);
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			} else {
				response.put("data", null);
				response.put("status", true);
				response.put("message", "Data tidak ditemukan");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.put("data", null);
			response.put("status", false);
			response.put("message", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping ("/postBlogContent")
	public ResponseEntity<Map<String, Object>> postBlogContent(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		BlogModel blogModel = new BlogModel();
		try {
			Date date = new Date();
			blogModel.setTitle(param.get("title").toString());
			blogModel.setBody(param.get("body").toString());
			blogModel.setAuthor(param.get("author").toString());
			blogModel.setCreateDate(date);
			blogModel.setUpdateDate(null);
			blogModel.setIsDelete(false);
			BlogModel blogObj = blogRepo.save(blogModel);
			
			response.put("data", blogObj);
			response.put("status", true);
			response.put("message", "Data berhasil ditambahkan");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("data", null);
			response.put("status", false);
			if (!param.containsKey("title") || !param.containsKey("body") || !param.containsKey("author")){
				response.put("message", "parameter tidak boleh kosong");
			} else {
				response.put("message", e.getMessage());
			}
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping ("/updateBlogContent")
	public ResponseEntity<Map<String, Object>> updateBlogContent(@RequestBody Map<String, Object> param) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		BlogModel blogModel = new BlogModel();
		BlogModel result = new BlogModel();
		Date date = new Date();
		try {
			Long id = Long.parseLong(param.get("id").toString());
			Optional<BlogModel> data = blogRepo.findByIdAndIsDelete(id, false);
			
			if (data.isPresent()){
				blogModel = data.get();
				blogModel.setTitle(param.get("title").toString());
				blogModel.setAuthor(param.get("author").toString());
				blogModel.setBody(param.get("body").toString());
				blogModel.setUpdateDate(date);
				
				result = blogRepo.save(blogModel);
				response.put("data", result);
				response.put("message", "Data berhasil diubah");
				response.put("status", true);
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			} else {
				response.put("data", null);
				response.put("message", "Data tidak ditemukan");
				response.put("status", true);
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.put("data", null);
			response.put("status", false);
			if (!param.containsKey("id") || !param.containsKey("title") || !param.containsKey("body") || !param.containsKey("author")){
				response.put("message", "parameter tidak boleh kosong");
			} else {
				response.put("message", e.getMessage());
			}
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping ("/deleteBlogById/{id}")
	public ResponseEntity<Map<String, Object>> deleteBlogById(@PathVariable Long id) throws Exception {
		Map<String, Object> response = new HashMap<String, Object>();
		BlogModel blogModel = new BlogModel();
		BlogModel result = new BlogModel();
		Date date = new Date();
		try {
			Optional<BlogModel> data = blogRepo.findByIdAndIsDelete(id, false);
			
			if (data.isPresent()){
				blogModel = data.get();
				blogModel.setIsDelete(true);
				blogModel.setUpdateDate(date);
				
				result = blogRepo.save(blogModel);
				response.put("data", result);
				response.put("message", "Data berhasil dihapus");
				response.put("status", true);
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			} else {
				response.put("data", null);
				response.put("message", "Data tidak ditemukan");
				response.put("status", true);
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.put("data", null);
			response.put("status", false);
			response.put("message", e.getMessage());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
