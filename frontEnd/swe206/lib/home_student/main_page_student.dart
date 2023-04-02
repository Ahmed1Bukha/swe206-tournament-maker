import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/authintication/auth.dart';
import 'package:swe206/home_student/home_page_student.dart';
import 'package:swe206/home_student/search_page_student.dart';

class MainPageStudent extends StatefulWidget {
  MainPageStudent({super.key});
  static String id = "MainPageStudent";
  @override
  State<MainPageStudent> createState() => _MainPageStudentState();
  int _selectedIndex = 0;
}

class _MainPageStudentState extends State<MainPageStudent> {
  static const List<Widget> _widgetOptions = <Widget>[
    HomePageStudent(),
    SearchPageStudent(),
    Text("LogOut")
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: widget._selectedIndex,
        onTap: (value) {
          setState(() {
            widget._selectedIndex = value;
          });
          if (widget._selectedIndex == 2) {
            Navigator.pushReplacementNamed(context, AuthPage.id);
          }
        },
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: "Home",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.search),
            label: "Search",
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.door_back_door_outlined),
            label: "Logout",
          ),
        ],
      ),
      body: _widgetOptions.elementAt(widget._selectedIndex),
    );
  }
}
