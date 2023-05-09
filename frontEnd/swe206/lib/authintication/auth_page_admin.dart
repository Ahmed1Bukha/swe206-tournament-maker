// ignore_for_file: no_leading_underscores_for_local_identifiers

import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:swe206/home_admin/main_page_admin.dart';
import 'package:swe206/requests.dart';

class AuthPageAdmin extends StatefulWidget {
  const AuthPageAdmin({super.key});
  static String id = "AuthPageAdmin";

  @override
  State<AuthPageAdmin> createState() => _AuthPageAdminState();
}

class _AuthPageAdminState extends State<AuthPageAdmin> {
  TextEditingController myUserName = TextEditingController();
  TextEditingController myPassword = TextEditingController();
  final _formKey = GlobalKey<FormState>();
  bool isLoading = false;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Form(
          key: _formKey,
          child: Padding(
            padding: const EdgeInsets.all(24.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                GestureDetector(
                  onTap: () {
                    Navigator.pop(context);
                  },
                  child: Icon(
                    Icons.arrow_back_ios,
                    size: 30,
                  ),
                ),
                const Center(
                  child: Text(
                    "Admin Auth",
                    style: TextStyle(
                      color: Colors.blue,
                      fontSize: 60,
                      fontFamily: "Mplus",
                      fontWeight: FontWeight.w700,
                    ),
                  ),
                ),
                SizedBox(
                  height: 40,
                ),
                Center(
                  child: SvgPicture.asset(
                    "lib/assets/svg/icon-admin.svg",
                    width: MediaQuery.of(context).size.width - 200,
                  ),
                ),
                SizedBox(
                  height: 100,
                ),
                TextFormField(
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter some text';
                    }
                    return null;
                  },
                  controller: myUserName,
                  cursorColor: Colors.black,
                  style: const TextStyle(
                    color: Colors.black,
                    fontSize: 20,
                  ),
                  decoration: InputDecoration(
                    prefixIcon: Icon(Icons.person),
                    hintText: "Entet UserName",
                    filled: true,
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(20),
                      borderSide: BorderSide.none,
                    ),
                  ),
                ),
                SizedBox(
                  height: 20,
                ),
                TextFormField(
                  validator: (value) {
                    if (value == null || value.isEmpty) {
                      return 'Please enter some text';
                    }
                    return null;
                  },
                  controller: myPassword,
                  obscureText: true,
                  cursorColor: Colors.black,
                  style: const TextStyle(
                    color: Colors.black,
                    fontSize: 20,
                  ),
                  decoration: InputDecoration(
                    prefixIcon: Icon(
                      Icons.password,
                    ),
                    hintText: "Entet Password",
                    filled: true,
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(20),
                      borderSide: BorderSide.none,
                    ),
                  ),
                ),
                SizedBox(
                  height: 20,
                ),
                Center(
                  child: ElevatedButton(
                      onPressed: () async {
                        if (_formKey.currentState!.validate()) {
                          setState(() {
                            isLoading = true;
                          });
                          var response = await Requests.authUser(
                              myUserName.text, myPassword.text);
                          setState(() {
                            isLoading = false;
                          });
                          if (response == "IsAdmin") {
                            Navigator.pushReplacementNamed(
                                context, MainPageAdmin.id);
                          } else {
                            showError(context);
                          }
                        }
                      },
                      child: !isLoading
                          ? const SizedBox(
                              height: 50,
                              width: 180,
                              child: Center(
                                child: Text(
                                  "Sign In",
                                  style: TextStyle(
                                    fontSize: 30,
                                  ),
                                ),
                              ),
                            )
                          : SizedBox(
                              height: 50,
                              width: 180,
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: const [
                                  CircularProgressIndicator(
                                    color: Colors.white,
                                  ),
                                  SizedBox(
                                    width: 20,
                                  ),
                                  Text(
                                    "Please wait...",
                                    style: TextStyle(
                                      fontSize: 15,
                                    ),
                                  )
                                ],
                              ),
                            )),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}

Future<String?> showError(BuildContext context) {
  return showDialog<String>(
    context: context,
    builder: (BuildContext context) => AlertDialog(
      title: const Text('Sign In Failed'),
      content: const Text(
          'Make sure that you entered the right username and password.'),
      actions: <Widget>[
        TextButton(
          onPressed: () => Navigator.pop(context, 'OK'),
          child: const Text('OK'),
        ),
      ],
    ),
  );
}
