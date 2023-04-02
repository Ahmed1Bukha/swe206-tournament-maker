import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';

class SearchPageStudent extends StatefulWidget {
  const SearchPageStudent({super.key});
  static String id = "SeachPageStudent";
  @override
  State<SearchPageStudent> createState() => _SearchPageStudentState();
}

class _SearchPageStudentState extends State<SearchPageStudent> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Search page"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          children: const [
            TextField(
              decoration: InputDecoration(
                label: Text("Search"),
                hintText: "Enter Tournament name",
                border: OutlineInputBorder(),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
