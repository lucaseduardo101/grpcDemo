import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

plugins {
	id("org.springframework.boot") version "2.6.4"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
	id ("com.google.protobuf") version "0.8.18"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	//gRPC
	implementation("io.grpc:grpc-netty:1.44.1")
	implementation("io.grpc:grpc-protobuf:1.44.1")
	implementation ("io.grpc:grpc-stub:1.44.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

protobuf {
	generatedFilesBaseDir = "$buildDir/generated/sources/proto"
	protoc {
		artifact = "com.google.protobuf:protoc:3.12.2"
	}

	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.30.0"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc"){
					outputSubDir = "grpc"
				}
			}
		}
	}
}

sourceSets{
	main{
		proto{
			srcDir("$buildDir/generated/sources/proto/main/grpc")
		}
	}
}
